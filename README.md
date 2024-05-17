Есть 2 ручки на первое и второе задание соотвественно:
* /production-callendar/isweekend/2024/may?day=...  
В query param day указывается день в формате dd  
Пример можно посмотреть с:  
curl --location 'http://localhost:8080/production-callendar/isweekend?day=10'
* /production-callendar/isfreetime  
Пример можно посмотреть с:  
curl --location 'http://localhost:8080/production-callendar/isfreetime' \
--header 'Content-Type: application/json' \
--data '{
    "date":"2024-05-08T17:00:01",
    "timezone":"Europe/Paris"
}'