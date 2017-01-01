
# Create session
new-session -Ad -c ~/Workspace/Bank/ -n src -s bank '/bin/bash --rcfile devrc'
set-option -t bank status-style "bg=colour235"
set-option -a -t bank status-style "fg=colour39"
set-option -t bank default-command "/bin/bash --rcfile devrc"

# Windows

## 1. git
new-window -n git -t bank

## 2. db
new-window -n db -t bank

## 3. mvn
new-window -n mvn -t bank

## 4. test
new-window -n test -t bank

## 5. deploy
new-window -n deploy -t bank

## 6. cloud
new-window -n cloud -t bank

# Notification
display "Session bank created"

