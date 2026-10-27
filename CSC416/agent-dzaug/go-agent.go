//Duncan Zaug
//CSC482
//go-agent
//Has 4 environment variables: POLLING_RATE, LOGGLY_TOKEN, AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY
// - POLLING_RATE: How often, in minutes, that the program will poll the API.
// - LOGGLY_TOKEN: the loggly token being used
// - AWS_ACCESS_KEY_ID: aws access key
// - AWS_SECRET_ACCESS_KEY: aws secret key


package main

//imports
import (
  "context"
  b64 "encoding/base64"
  "encoding/json"
  "fmt"
  "github.com/aws/aws-sdk-go-v2/aws"
  "github.com/aws/aws-sdk-go-v2/config"
  "github.com/aws/aws-sdk-go-v2/service/dynamodb"
  "github.com/aws/aws-sdk-go-v2/service/dynamodb/types"
  "github.com/jamespearly/loggly"
  "io/ioutil"
  "net/http"
  "os"
  "strconv"
  "strings"
  "time"
)


//json parsing types
type Response struct {
  Id string
  Type string
  Geometry Geometry
  Properties Properties //`json:"properties"`
}
type Geometry struct {
  Type string
  Coordinates []float64 `json:"coordinates"`
}
type Properties struct {
  Station string
  Timestamp string
  RawMessage string
  TextDescription string
  Temperature DataValues
  Dewpoint DataValues
  WindDirection DataValues
  WindSpeed DataValues
  WindGust DataValues
  BarometricPressure DataValues
  SeaLevelPressure DataValues
  Visibility DataValues
  RelativeHumidity DataValues
  WindChill DataValues
  HeatIndex DataValues
}
type DataValues struct {
  UnitCode string
  Value float64
  QualityControl string
}
type tableStructure struct {
  TimeStamp string
  RawMessage string
  TextDescription string
  Temperature string
  Dewpoint string
  WindDirection string
  WindSpeed string
  WindGust string
  BarometricPressure string
  SeaLevelPressure string
  Visibility string
  RelativeHumidity string
  WindChill string
  HeatIndex string
}

//main function
func main() {
  //awsClientSetup
  cfg, err := config.LoadDefaultConfig(context.TODO(), func(opts *config.LoadOptions) error {
    opts.Region = "us-east-1"
    return nil
  })
  if err != nil {
    panic(err)
  }
  awsClient := dynamodb.NewFromConfig(cfg)
  //logClient setup
  var tag string
  tag = "GO-Program-Log"
  logClient := loggly.New(tag)
  //APIClient setup
  APIClient := http.Client{Timeout: time.Duration(10) * time.Second}
  //ticker moment
  //send message every specified value
  var rawRate = os.Getenv("POLLING_RATE")
  var pollingRate float64
  pollingRate, _ = strconv.ParseFloat(rawRate, 64)
  ticker := time.NewTicker(time.Duration(pollingRate) * time.Minute)
  fmt.Println("agent active at:", time.Now(), " \n")
  //messageSender(APIClient, logClient, awsClient, time.Now())
  for _ = range ticker.C {
    messageSender(APIClient, logClient, awsClient, time.Now())
  }

}


//message sender function
func messageSender(APIClient http.Client,logClient *loggly.ClientType, awsClient *dynamodb.Client, t time.Time) {
  //api stuff
  resp, err := APIClient.Get("https://api.weather.gov/stations/KFZY/observations/latest")
  //error handling
  if err != nil {
    fmt.Printf("Error %s", err)
    return
  }
  //http request
  req, err := http.NewRequest("GET", "https://api.weather.gov/stations/KFZY/observations/latest", nil)
  //error handling
  if err != nil {
    fmt.Printf("Error %s", err)
    return
  }
  //add header values
  req.Header.Add("User-Agent", "SUNY Oswego College Project")
  req.Header.Add("User-Agent", "dzaug@oswego.edu")
  //sending request
  resp, err = APIClient.Do(req)
  //error handling
  if err != nil {
    fmt.Printf("Error %s", err)
    return
  }
  defer resp.Body.Close()
  body, err := ioutil.ReadAll(resp.Body)
  if err != nil {
    fmt.Printf("Error %s", err)
    return
  }
  //debug print
  //fmt.Printf("Body : %s", body)
  //fmt.Println(" \n")
  //formating response
  var response Response
  json.Unmarshal(body, &response)
  //response t
  var station = "Station: " + response.Properties.Station + "\n"
  var timeStamp = "Timestamp: " + response.Properties.Timestamp + "\n"
  var rawMessage = "Raw Message: " + response.Properties.RawMessage + "\n"
  var textDescription = "Text Description: " + response.Properties.TextDescription + "\n"
  unit, code := strings.CutPrefix(response.Properties.Temperature.UnitCode, "wmoUnit:")
  var temperature = "Temperature: " +
      strconv.FormatFloat(response.Properties.Temperature.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.Dewpoint.UnitCode, "wmoUnit:")
  var dewpoint = "Dewpoint: " +
      strconv.FormatFloat(response.Properties.Dewpoint.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.WindDirection.UnitCode, "wmoUnit:")
  var windDirection = "Wind Direction: " +
      strconv.FormatFloat(response.Properties.WindDirection.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.WindSpeed.UnitCode, "wmoUnit:")
  var windSpeed = "Wind Direction: " +
      strconv.FormatFloat(response.Properties.WindSpeed.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.WindGust.UnitCode, "wmoUnit:")
  var windGust = "Wind Gust: " +
      strconv.FormatFloat(response.Properties.WindGust.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.BarometricPressure.UnitCode, "wmoUnit:")
  var barometricPressure = "Barometric Pressure: " +
      strconv.FormatFloat(response.Properties.BarometricPressure.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.SeaLevelPressure.UnitCode, "wmoUnit:")
  var seaLevelPressure = "Sea Level Pressure: " +
      strconv.FormatFloat(response.Properties.SeaLevelPressure.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.Visibility.UnitCode, "wmoUnit:")
  var visibility = "Visibility: " +
      strconv.FormatFloat(response.Properties.Visibility.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.RelativeHumidity.UnitCode, "wmoUnit:")
  var relativeHumidity = "Relative Humidity: " +
      strconv.FormatFloat(response.Properties.RelativeHumidity.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.WindChill.UnitCode, "wmoUnit:")
  var windChill = "Wind Chill: " +
      strconv.FormatFloat(response.Properties.WindChill.Value, 'f', -1, 64) +
      " " + unit +"\n"
  unit, code = strings.CutPrefix(response.Properties.HeatIndex.UnitCode, "wmoUnit:")
  var heatIndex = "Heat Index: " +
      strconv.FormatFloat(response.Properties.HeatIndex.Value, 'f', -1, 64) +
      " " + unit +"\n"
  if code == false {
    fmt.Printf("Error %s", err)
  }
  var infoToPrint = station + timeStamp + rawMessage + textDescription + temperature + dewpoint + windDirection +
      windSpeed + windGust + barometricPressure + seaLevelPressure + visibility + relativeHumidity + windChill + heatIndex
  var messageToSend = response.Properties.RawMessage //rawMessage + timeStamp
  err = logClient.Send("info", messageToSend)
  if err != nil {
    fmt.Println("Error Sending Message at", t)
    return
  } else {
    putItemInTable(response, awsClient, t)
    fmt.Println(infoToPrint)
    fmt.Println("Message Sent at", t, "\n\n")

  }

}

func putItemInTable(response Response, awsClient *dynamodb.Client, t time.Time) {
  //setting up the table item that will be written
  unit1, _ := strings.CutPrefix(response.Properties.Temperature.UnitCode, "wmoUnit:")
  unit2, _ := strings.CutPrefix(response.Properties.Dewpoint.UnitCode, "wmoUnit:")
  unit3, _ := strings.CutPrefix(response.Properties.WindDirection.UnitCode, "wmoUnit:")
  unit4, _ := strings.CutPrefix(response.Properties.WindSpeed.UnitCode, "wmoUnit:")
  unit5, _ := strings.CutPrefix(response.Properties.WindGust.UnitCode, "wmoUnit:")
  unit6, _ := strings.CutPrefix(response.Properties.BarometricPressure.UnitCode, "wmoUnit:")
  unit7, _ := strings.CutPrefix(response.Properties.SeaLevelPressure.UnitCode, "wmoUnit:")
  unit8, _ := strings.CutPrefix(response.Properties.Visibility.UnitCode, "wmoUnit:")
  unit9, _ := strings.CutPrefix(response.Properties.RelativeHumidity.UnitCode, "wmoUnit:")
  unit10, _ := strings.CutPrefix(response.Properties.WindChill.UnitCode, "wmoUnit:")
  unit11, _ := strings.CutPrefix(response.Properties.HeatIndex.UnitCode, "wmoUnit:")
  var item = tableStructure {
    response.Properties.Timestamp,
    response.Properties.RawMessage,
    response.Properties.TextDescription,
    strconv.FormatFloat(response.Properties.Temperature.Value, 'f', -1, 64) + " " + unit1,
    strconv.FormatFloat(response.Properties.Dewpoint.Value, 'f', -1, 64) + " " + unit2,
    strconv.FormatFloat(response.Properties.WindDirection.Value, 'f', -1, 64) + " " + unit3,
    strconv.FormatFloat(response.Properties.WindSpeed.Value, 'f', -1, 64) + " " + unit4,
    strconv.FormatFloat(response.Properties.WindGust.Value, 'f', -1, 64) + " " + unit5,
    strconv.FormatFloat(response.Properties.BarometricPressure.Value, 'f', -1, 64) + " " + unit6,
    strconv.FormatFloat(response.Properties.SeaLevelPressure.Value, 'f', -1, 64) + " " + unit7,
    strconv.FormatFloat(response.Properties.Visibility.Value, 'f', -1, 64) + " " + unit8,
    strconv.FormatFloat(response.Properties.RelativeHumidity.Value, 'f', -1, 64) + " " + unit9,
    strconv.FormatFloat(response.Properties.WindChill.Value, 'f', -1, 64) + " " + unit10,
    strconv.FormatFloat(response.Properties.HeatIndex.Value, 'f', -1, 64) + " " + unit11,
  }
  //putting in the item
  id := b64.StdEncoding.EncodeToString([]byte(t.String()))
  out, err := awsClient.PutItem(context.TODO(), &dynamodb.PutItemInput{
    TableName: aws.String("dzaug_table"),
    Item: map[string]types.AttributeValue{
      "id": &types.AttributeValueMemberS{Value: id},
      "TimeStamp": &types.AttributeValueMemberS{Value: item.TimeStamp},
      "RawMessage": &types.AttributeValueMemberS{Value: item.RawMessage},
      "TextDescription": &types.AttributeValueMemberS{Value: item.TextDescription},
      "Temperature": &types.AttributeValueMemberS{Value: item.Temperature},
      "Dewpoint": &types.AttributeValueMemberS{Value: item.Dewpoint},
      "WindDirection": &types.AttributeValueMemberS{Value: item.WindDirection},
      "WindSpeed": &types.AttributeValueMemberS{Value: item.WindSpeed},
      "WindGust": &types.AttributeValueMemberS{Value: item.WindGust},
      "BarometricPressure": &types.AttributeValueMemberS{Value: item.BarometricPressure},
      "SeaLevelPressure": &types.AttributeValueMemberS{Value: item.SeaLevelPressure},
      "Visibility": &types.AttributeValueMemberS{Value: item.Visibility},
      "RelativeHumidity": &types.AttributeValueMemberS{Value: item.RelativeHumidity},
      "WindChill": &types.AttributeValueMemberS{Value: item.WindChill},
      "HeatIndex": &types.AttributeValueMemberS{Value: item.HeatIndex},
    },
  })
  if err != nil {
    panic(err)
  }
  fmt.Println(out.Attributes)
}

