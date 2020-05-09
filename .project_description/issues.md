# Issues.

1. flyway при запуске миграции данные добавляет, но выдает предупреждение:
[WARNING] DB: Unknown table 'gb_spring_ishop_lys.products' (SQL State: 42S02 - Error Code: 1051);
Solution: just ignore(lys)
Conclusion: Похоже flyway ругается на строку drop table products cascade;
Если запустить еще раз миграцию(без очистки) то все работает - 
[INFO] Schema `gb_spring_ishop_lys` is up to date. No migration necessary.

2. 