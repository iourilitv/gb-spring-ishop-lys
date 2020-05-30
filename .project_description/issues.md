# Issues.

##1. flyway при запуске миграции данные добавляет, но выдает предупреждение:
[WARNING] DB: Unknown table 'gb_spring_ishop_lys.products' (SQL State: 42S02 - Error Code: 1051);
Solution: just ignore(lys)
Conclusion: Похоже flyway ругается на строку drop table products cascade;
Если запустить еще раз миграцию(без очистки) то все работает - 
[INFO] Schema `gb_spring_ishop_lys` is up to date. No migration necessary.

##2. Thymeleaf + Spring.
 Не смог реализовать совместную фильтрацию по ссылкам на категории в header.html 
 и в фильтре. Все работает отдельно. Либо ссылки, либо фильтр. При применении фильтра слетают ссылки и наоборот. 
 После добавления параметра category в filterDef, появляется дублирование этого параметра.
 Возможное решение: 
 в файле header.html в ссылке в элементе меню удалить этот параметр из filterDef 
 и добавить новый параметр.
 Код не работает:
                             <!-- does not work -->
 <!--                            <li th:class="${#strings.equals(param.category, category.id) ? 'nav-item nav-pills' : 'nav-item'}">-->
 <!--                                <a th:class="${#strings.equals(param.category, category.id) ? 'nav-link active color-white' : 'nav-link active'}"-->
 <!--                                   th:href="@{'/catalog/all' + ${__${#strings.replace(${filterDef},-->
 <!--                                   'category=' + ${param.category}, 'category=' + ${category.id})}__}}"-->
 <!--                                   th:text="${category.title}"></a>-->
 <!--                            </li>-->
 Нужно, либо найти синтаксис препроцессора thymeleaf, либо делать это на стороне StringBuilderа, 
 либо создать свой класс filterDef вместо StringBuilder.
 В своем классе можно реализовать метод замены значения параметра.
 А вообще лучше все фильтры делать в одном месте. 
 Пока удалил ссылки категорий из header, и добавил категории в фильтр каталога.
  
##3. CSS-styles does not change.
Не могу поменять стили у кнопок в форме фильтра.
Почему-то стили у button в style.css перебивают мои в my-changes.css, хотя ссылка 
на мою таблицу стилей стоит и позже. Странно!?

##4. Не работает ajax.
Страница все равно обновляется при нажатии на кнопку Add To Cart в каталоге
куда бы не добавить в catalog.html строку
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>.


  