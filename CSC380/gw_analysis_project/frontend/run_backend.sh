#!/bin/bash
sshpass -p '{insert_ssh_password_here}' ssh -o StrictHostKeyChecking=no root@backend 'python3 /src/terminal_interface.py'
exit
