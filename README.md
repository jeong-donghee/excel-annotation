# Response Excel file with annotation
``` java
@ExcelResponse
@GetMapping("/excel")
public ResponseEntity<ResponseDto> getResponse(...) {...}
```
It works with AOP.
⚠️The DTO must be wrapped in a ResponseEntity.

``` java
@ExcelIgnore
private String id;
```
Prevents the annotated field from being included in the generated Excel file.

``` java
@ExcelProperty(name="currentTime", convert=LocalDateTimeConverter.class)
private LocalDateTime time;
```
Customizes the Excel column header name and/or specifies a value converter.

``` java
public class MyCustomConverter implements ExcelConverter<String> {
  @Override
  public String convert(String value) {
    return value != null ? "Value is : " + value : "";
  }
}
```
You can implement ExcelConverter and specify it using the convert attribute to customize how a field is rendered in Excel.
