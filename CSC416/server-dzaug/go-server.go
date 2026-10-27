//Duncan Zaug
//CSC482
//HTTP Server
//Has 3 environment variables: LOGGLY_TOKEN, AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY
// - LOGGLY_TOKEN: the loggly token being used
// - AWS_ACCESS_KEY_ID: aws access key
// - AWS_SECRET_ACCESS_KEY: aws secret key

package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/feature/dynamodb/expression"
	"github.com/aws/aws-sdk-go-v2/service/dynamodb"
	"github.com/gorilla/mux"
	"github.com/jamespearly/loggly"
	"log"
	"net"
	"net/http"
	"net/url"
	"os"
	"strconv"
	"strings"
	"time"
)

func status(w http.ResponseWriter, r *http.Request) {
	var tag string
	tag = "GO-Program-Log"
	logClient := loggly.New(tag)
	var messageToSend string
	logger := log.New(os.Stdout, "http: ", log.LstdFlags)
	clientIP, _, err := net.SplitHostPort(r.RemoteAddr)
	if err != nil {
		return
	}
	switch r.Method {
	case "GET":
		messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusOK) + " from " + clientIP
		logger.Println(messageToSend)
		err = logClient.Send("info", messageToSend)
		if err != nil {
			return
		}
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Got Request at " + time.Now().String()))
	default:
		messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusMethodNotAllowed) + " from " + clientIP
		logger.Println(messageToSend)
		err := logClient.Send("info", messageToSend)
		if err != nil {
			return
		}
		w.WriteHeader(http.StatusMethodNotAllowed)
		w.Write([]byte(http.StatusText(http.StatusMethodNotAllowed)))
	}
}

type tableOverview struct {
	TableName string
	Count     int32
}

func all(w http.ResponseWriter, r *http.Request) {
	//logging and IP Acquisition
	var tag string
	tag = "GO-Program-Log"
	logClient := loggly.New(tag)
	logger := log.New(os.Stdout, "http: ", log.LstdFlags)
	clientIP, _, err := net.SplitHostPort(r.RemoteAddr)
	if err != nil {
		return
	}
	switch r.Method {
	case "GET":
		//aws stuff
		cfg, err := config.LoadDefaultConfig(context.TODO(), func(opts *config.LoadOptions) error {
			opts.Region = "us-east-1"
			return nil
		})
		if err != nil {
			panic(err)
		}
		awsClient := dynamodb.NewFromConfig(cfg)
		out, err := awsClient.Scan(context.TODO(), &dynamodb.ScanInput{
			TableName: aws.String("dzaug_table"),
		})
		if err != nil {
			panic(err)
		}
		result := tableOverview{
			"dzaug_table",
			out.Count,
		}
		jsonItem, _ := json.Marshal(result)
		//logging section
		var messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusOK) + " from " + clientIP
		logger.Println(messageToSend)
		err = logClient.Send("info", messageToSend)
		if err != nil {
			return
		}
		//returning values
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		_, err = w.Write(jsonItem)
		if err != nil {
			return
		}
	default:
		var messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusMethodNotAllowed) + " from " + clientIP
		logger.Println(messageToSend)
		err := logClient.Send("info", messageToSend)
		if err != nil {
			return
		}
		w.WriteHeader(http.StatusMethodNotAllowed)
		w.Write([]byte(http.StatusText(http.StatusMethodNotAllowed)))
	}
}
func search(w http.ResponseWriter, r *http.Request) {
	//setup stuff
	var tag string
	tag = "GO-Program-Log"
	logClient := loggly.New(tag)
	logger := log.New(os.Stdout, "http: ", log.LstdFlags)
	clientIP, _, err := net.SplitHostPort(r.RemoteAddr)
	if err != nil {
		return
	}
	var messageToSend string
	//method check
	switch r.Method {
	case "GET":
		//url param check
		var rawQ = r.URL.RawQuery
		q, err := url.ParseQuery(rawQ)
		if err != nil {
			panic(err)
		}
		qjs, _ := json.Marshal(q)
		var qResult map[string]any
		json.Unmarshal(qjs, &qResult)
		if len(qResult) != 1 {
			//more than one param
			messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusBadRequest) + " from " + clientIP
			logger.Println(messageToSend)
			err := logClient.Send("info", messageToSend)
			if err != nil {
				return
			}
			w.WriteHeader(http.StatusBadRequest)
			w.Write([]byte(http.StatusText(http.StatusBadRequest) + "\nMore than one attribute is trying to be asked for."))
			return
		} else if !(url.Values.Has(q, "timestamp")) && !(url.Values.Has(q, "id")) {
			//if it is not id or timestamp
			messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusBadRequest) + " from " + clientIP
			logger.Println(messageToSend)
			err := logClient.Send("info", messageToSend)
			if err != nil {
				return
			}
			w.WriteHeader(http.StatusBadRequest)
			w.Write([]byte(http.StatusText(http.StatusBadRequest) + "\nAttribute is not id or timestamp!"))
			return
		} else {
			//it is valid
			var attribute string
			var attributeValue string
			if url.Values.Has(q, "id") {
				attribute = "id"
				attributeValue = url.Values.Get(q, "id")
			} else {
				attribute = "TimeStamp"
				var rawAttributeValue = url.Values.Get(q, "timestamp")
				fmt.Println(rawAttributeValue)
				attributeValue = strings.Replace(rawAttributeValue, " ", "+", -1)
			}
			//aws stuff
			cfg, err := config.LoadDefaultConfig(context.TODO(), func(opts *config.LoadOptions) error {
				opts.Region = "us-east-1"
				return nil
			})
			if err != nil {
				panic(err)
			}
			awsClient := dynamodb.NewFromConfig(cfg)
			expr, err := expression.NewBuilder().WithFilter(
				expression.Contains(expression.Name(attribute), attributeValue),
			).Build()
			if err != nil {
				panic(err)
			}
			out, err := awsClient.Scan(context.TODO(), &dynamodb.ScanInput{
				TableName:                 aws.String("dzaug_table"),
				FilterExpression:          expr.Filter(),
				ExpressionAttributeNames:  expr.Names(),
				ExpressionAttributeValues: expr.Values(),
			})
			if err != nil {
				panic(err)
			}
			//logging section
			messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusOK) + " from " + clientIP
			logger.Println(messageToSend)
			err = logClient.Send("info", messageToSend)
			if err != nil {
				return
			}
			//returning values
			jsonItem, _ := json.Marshal(out.Items)
			w.Header().Set("Content-Type", "application/json")
			w.WriteHeader(http.StatusOK)
			_, err = w.Write(jsonItem)
			if err != nil {
				return
			}
		}
	default:
		messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusMethodNotAllowed) + " from " + clientIP
		logger.Println(messageToSend)
		err := logClient.Send("info", messageToSend)
		if err != nil {
			return
		}
		w.WriteHeader(http.StatusMethodNotAllowed)
		w.Write([]byte(http.StatusText(http.StatusMethodNotAllowed)))
	}
}
func catchAll(w http.ResponseWriter, r *http.Request) {
	var tag string
	tag = "GO-Program-Log"
	logClient := loggly.New(tag)
	logger := log.New(os.Stdout, "http: ", log.LstdFlags)
	clientIP, _, err := net.SplitHostPort(r.RemoteAddr)
	if err != nil {
		return
	}
	var messageToSend = "Got Request on " + time.Now().String() + " at " + r.URL.Path + " with status " + strconv.Itoa(http.StatusNotFound) + " from " + clientIP
	logger.Println(messageToSend)
	err = logClient.Send("info", messageToSend)
	if err != nil {
		return
	}
	w.WriteHeader(http.StatusNotFound)
	w.Write([]byte(http.StatusText(http.StatusNotFound)))
}

func main() {
	logger := log.New(os.Stdout, "http: ", log.LstdFlags)
	router := mux.NewRouter()
	router.HandleFunc("/dzaug/status", status)
	router.HandleFunc("/dzaug/all", all)
	router.HandleFunc("/dzaug/search", search)
	router.PathPrefix("/").HandlerFunc(catchAll)
	logger.Println("Server is running")
	err := http.ListenAndServe(":8080", router)
	if err != nil {
		logger.Fatal("fatal error: ", err)
	}
}

