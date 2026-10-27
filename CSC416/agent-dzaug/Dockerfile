#base image
FROM public.ecr.aws/docker/library/golang:latest AS build

# Copy source
WORKDIR /build

#get files
COPY go.mod go.sum ./
RUN go mod download
COPY *.go ./

#environment variables
ENV LOGGLY_TOKEN=$LOGGLY_TOKEN
ENV POLLING_RATE=$POLLING_RATE

#create excutable 
RUN CGO_ENABLED=0 GOOS=linux go build -o main .

FROM public.ecr.aws/docker/library/alpine:latest

RUN apk update && \
    apk upgrade && \
    apk add ca-certificates && \
    apk add tzdata

#work directory
WORKDIR /app

# Copy files from previous build container
COPY --from=build /build/main ./

RUN pwd && find .

#ports for requests
EXPOSE 8080
EXPOSE 80
EXPOSE 443
EXPOSE 8443


CMD ["./main"]
