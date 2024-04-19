FROM python:3.10

WORKDIR /python-docker

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt
RUN apt-get update && apt-get install -y iputils-ping netcat-traditional openssh-client sshpass

COPY . .

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]
