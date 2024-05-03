cmake_minimum_required(VERSION 3.26)
project(shlab C)

set(CMAKE_C_STANDARD 11)

add_executable(shlab tsh.c
        tsh.c
        myint.c
        myspin.c
        mysplit.c
        mystop.c)
