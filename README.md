# rundeck-httpNotificationPlugin
======================

Advanced Http Notification for Rundeck Jobs. 

Installation Instructions
-------------------------

1. Build the source with gradle
```sh
./gradlew build
```
2. Copy the plugin jar into your $RDECK_BASE/libext. 
```sh
cp httpNotificationPlugin.jar $RDECK_BASE/libext
```
*No restart required


## Configuration

The only required configuration settings are:

- `Request Http Method`: Choose between "GET","POST","PUT","PATCH","HEAD","OPTIONS","DELETE"
- `Http Body`: Content to put or post.
- `Content Type`: Content type to use for request body
- `URL Target of http request`: Url target of http request.


![screenshot](screenshot.png)
