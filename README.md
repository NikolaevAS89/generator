# Постановка Задачи
Требуется приложение для генерации больших объемов данных разного типа.

# Анализ
Для задания целевых таблиц для генерации данных будем использовать файл со следующей структурой строк:
    
    schema.table[|PK1,PK2, ... ,PKN[|schema.table.column1:[U|I], ... ,schema.table.columnN:[U|I]]]

Для задания специфичных генераторов для полей используем файл со следующей структурой строк:

    schema.table.column|GeneratorClassName

Для поддержки генерации данных разных типов для разных баз будем использовать JDBC.
Для обеспечения скорости генерации будем генерировать случайные данные но с достаточной вариативностью.

Для генерации значений ключа (или поля), если возникают колизии, необходимо иметь возможность задать
специфичный генератор значений.

Выполняемые запросы целесообразно разделить на пачки (batch) для уменьшения нагрузки на соединение и 
увеличении скорости обработки. Одна пачка - одна транзакция.
При этом логировать как успешно выполненые так и неуспешные транзакции, в разные файлы.
Разделение запросов поможет легче понять что упало и по какой причине, а так же скорректировать 
запрос и выполнить его в ручном режиме.

Для построения проекта используем Spring.

# Описание параметров 

параметр                                        | значение по умолчанию | Описание
------------------------------------------------|-----------------------|---------
connection.handler.db.connection                |                       | Строка подключения к БД.
connection.handler.db.type                      | ORACLE                | тип БД 
connection.handler.generator.type               | ORACLE_GENERATOR      | тип генератора
journal.fail.log                                | false                 | необходимо ли сохранять неуспешные запросы 
journal.fail.output                             | failed.sql            | путь к файлу куда будут записываться неуспешные запросы 
journal.succeeded.log                           | false                 |
journal.succeeded.output                        | succeeded.sql         |
generator.goal.scope                            | keys.csv              | путь к файлу с целевыми таблицами
generator.cron.expression                       | 0 * * * * *           | cron выражение для запуска по расписанию
generator.dml.row.insert.count                  | 2                     | количество вставок
generator.dml.row.update.count                  | 1                     | количество обновлений
generator.dml.row.delete.count                  | 1                     | количество удалений
generator.dml.batch.initSize.max                | 100                   | размер пачки (транзакции)
generator.data.blob.enable                      | false                 | генерировать blob значения
generator.data.clob.enable                      | false                 | генерировать clob значения
generator.data.bigdecimal.boundary.enable       | true                  | 
generator.data.bigdecimal.initPrecision.default | 18                    |
generator.data.bigdecimal.initPrecision.max     | 22                    |
generator.data.bigdecimal.initSize.default      | 18                    |
generator.data.bigdecimal.initScale.min         | -20                   |
generator.data.bigdecimal.initScale.max         | 999                   |
generator.data.bigdecimal.initSize.max          | 36                    |
generator.data.bigdecimal.initScale.default     | 2                     |
generator.data.string.initSize.max              | 100                   |
generator.data.string.initSize.default          | 20                    |
generator.data.string.boundary.enable           | true                  |

# Примечания
1. Параметр <i>connection.handler.db.connection</i> должен содержать строку подключения по JDBC:
    1. Oracle: <b>jdbc:oracle:thin:[username]/[password]@[server_name]:[port]:[service_name]</b>
    1. MSSQL: <b>jdbc:sqlserver:[server_name];user=[username];password=[password];databaseName=[db_name]</b>
1. Параметр <i>connection.handler.db.type</i> указывает тип базы:
    1. ORACLE
    1. MSSQL
    1. Название класса-наследника <b>DataBaseAgent</b>
1. Параметр <i>connection.handler.generator.type</i> указывает какой провайдер генераторов использовать: 
    1. ORACLE_GENERATOR
    1. MSSQL_GENERATOR
    1. Название класса-наследника <b>DataGeneratorService</b>
1. При отсутствии класса генератора для поля используется генератор NULL значений



