FROM ubuntu:22.04

#copying needed files from local machine for right now did just src and packages
#however once we aggeragted the info we can more properly setup the copy commands
#also /src/ means find directory named src or if not found make directory.
COPY packages.json /packages/
COPY src /src/
#setting work directroy
WORKDIR /src
#COPY profile/ /src/profile/
#COPY data/ /src/data/
#COPY pro/ /src/pro/
COPY . /
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Etc/UTC
#intall the required packages here
RUN apt-get update && apt-get install -y \ 
    libc6 \
    libc6-dev \
    libcap-dev \
    make \
    automake \
    cpp \
    gcc \
    gdb \
    g++ \
    zlib1g \
    gcc-12-base \
    libgcc-s1 \
    vim \
    curl \
    sudo \
    unzip \
    wget \
    build-essential \
    bzip2 \
    dpkg-sig \
    dpkg-dev \
    software-properties-common \
    cmake \
    python3 \
    python3-pip \
    python3-numpy \
    libpython3.10 \
    python3-scipy \
    python3-matplotlib \
    git \
    net-tools \
    traceroute \
    dnsutils \
    iputils-ping \
    xvfb \
    libgomp1 \
    libreadline8 \
    libstdc++6 \
    libwxbase3.0-0v5 \
    libwxgtk3.0-gtk3-0v5 \
    libgsl-dev \
    zlib1g-dev \
    libncurses5-dev \
    libgdbm-dev \
    libnss3-dev \ 
    libssl-dev \ 
    libreadline-dev \
    libffi-dev \
    plplot-driver-wxwidgets \
    libgnudatalanguage0 \
    gnudatalanguage \
    python3-gdl \
    python3-flask \
    nano \
    vim \
    openssh-server \
 && pip3 uninstall numpy \
 && pip3 install numpy==1.24.3 \
 && pip3 install fpdf \ 
 && pip3 install metpy \
 && pip3 install cartopy \
 && pip3 install flask \
 && rm -rf /var/lib/apt/lists/* 
  #just to confirm all packages are up to date
RUN apt-get update && apt-get full-upgrade -y
RUN sed -i 's/#PermitRootLogin prohibit-password/permitRootLogin yes/g' /etc/ssh/sshd_config
RUN sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/g' /etc/ssh/sshd_config
RUN sed -i 's/#PubkeyAuthentication yes/PubkeyAuthentication no/g' /etc/ssh/sshd_config
RUN echo "{insert_ssh_password_here}\n{insert_ssh_password_here}" | passwd root

#exposed port 22 to the local compose network
EXPOSE 22
#This is where we can execute code this is where we could call the main.py script or what not
ENTRYPOINT service ssh restart && bash
