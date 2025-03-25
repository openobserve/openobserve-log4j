# OpenObserve Log4j Integration

This project demonstrates how to integrate Log4j2 with OpenObserve to send application logs to your OpenObserve instance for centralized logging and analysis.

## Prerequisites

- Java 11 or newer
- Maven 3.6+
- An OpenObserve account with access credentials

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/openobserve-log4j.git
cd openobserve-log4j
```

### 2. Configure OpenObserve Connection

Edit the configuration in `src/main/resources/log4j2.xml` to point to your OpenObserve instance:

```xml
<Http name="OpenObserve" url="https://your-openobserve-instance/api/organization_name/stream_name/_json">
    <Property name="Content-Type" value="application/json"/>
    <!-- Basic authentication -->
    <Property name="Authorization" value="Basic ${base64:your_username:your_password}"/>
    
    <!-- JSON Layout for structured logging -->
    <JsonLayout compact="true" eventEol="true" properties="true">
        <KeyValuePair key="service" value="your-service-name"/>
    </JsonLayout>
</Http>
```

Replace:
- `your-openobserve-instance` with your OpenObserve domain
- `organization_name` with your organization name in OpenObserve
- `stream_name` with the desired stream name
- `your_username` and `your_password` with your OpenObserve credentials
- `your-service-name` with your application's name

### 3. Build the Project

```bash
mvn clean package
```

This will create an executable JAR with all dependencies included.

### 4. Run the Example Application

```bash
java -jar target/openobserve-log4j-1.0-SNAPSHOT.jar
```

## Project Structure

- `src/main/java/com/example/OpenObserveLog4jExample.java`: Example application with logging
- `src/main/resources/log4j2.xml`: Log4j2 configuration with OpenObserve HTTP appender
- `pom.xml`: Maven project configuration with required dependencies

## Dependencies

The project uses the following key dependencies:

- Log4j2 API and Core
- Log4j2 JSON Layout
- Jackson DataBind (for JSON processing)
- Apache HTTP Components (for HTTP communication)

## Configuration Details

### Maven Dependencies

```xml
<dependencies>
    <!-- Log4j Core -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>${log4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>${log4j.version}</version>
    </dependency>
    
    <!-- Log4j JSON Layout -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-layout-template-json</artifactId>
        <version>${log4j.version}</version>
    </dependency>
    
    <!-- Jackson dependencies for JSON processing -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.14.2</version>
    </dependency>
    
    <!-- HTTP components for HTTP appender -->
    <dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpclient</artifactId>
        <version>4.5.14</version>
    </dependency>
</dependencies>
```

### Log4j2 Configuration

The configuration in `log4j2.xml` sets up:

1. A Console appender for local debugging
2. An HTTP appender to send logs to OpenObserve
3. An Async wrapper for the HTTP appender to improve performance
4. Root logger configuration to use both appenders

## Troubleshooting

### Common Issues

1. **ClassNotFoundException: com.fasterxml.jackson.databind.ser.FilterProvider**
   
   Ensure the Jackson DataBind dependency is correctly included in your POM file.

2. **Unable to connect to OpenObserve**
   
   Verify your OpenObserve URL and credentials are correct.

3. **HTTP Connection Issues**
   
   Check network connectivity and firewall settings.

### Debug Mode

To enable more detailed logging for troubleshooting, change the status level in your Log4j2 configuration:

```xml
<Configuration status="DEBUG">
```

## Extended Features

### Adding Custom Fields

You can add custom fields to each log event:

```xml
<JsonLayout compact="true" eventEol="true" properties="true">
    <KeyValuePair key="service" value="your-service-name"/>
    <KeyValuePair key="environment" value="production"/>
    <KeyValuePair key="version" value="1.0.0"/>
</JsonLayout>
```

### Including Thread Context Data

To include thread context data (MDC) in logs:

```java
ThreadContext.put("user_id", "12345");
ThreadContext.put("session_id", "abcdef");
logger.info("User action performed");
```

## License

[MIT License](LICENSE)
