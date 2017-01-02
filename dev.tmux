
# Create session
new-session -Ad -c ~/Workspace/Expenses/ -n src -s expenses '/bin/bash --rcfile devrc'
set-option -t expenses status-style "bg=colour235"
set-option -a -t expenses status-style "fg=colour39"
set-option -t expenses default-command "/bin/bash --rcfile devrc"

# Windows

## 1. git
new-window -n git -t expenses

## 2. db
new-window -n db -t expenses

## 3. mvn
new-window -n mvn -t expenses

## 4. test
new-window -n test -t expenses

## 5. deploy
new-window -n deploy -t expenses

## 6. cloud
new-window -n cloud -t expenses

# Notification
display "Session expenses created"

