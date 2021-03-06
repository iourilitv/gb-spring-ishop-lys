drop table if exists categories cascade;
drop table if exists products cascade;

CREATE TABLE `categories` (
    `id` smallint NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
    COMMENT='SMALLINT == short';

INSERT INTO `categories` (`title`) VALUES
('Электроника'), ('Офисная техника'), ('Бытовая техника'),
('Обувь'), ('Одежда'), ('Книги'), ('Pets');

CREATE TABLE `products` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `description` text,
    `price` decimal(19,2) DEFAULT NULL,
    `category_id` smallint NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_qka6vxqdy1dprtqnx9trdd47c` (`title`),
    KEY `fk_category_id_idx` (`category_id`),
    CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `products` (`title`, `description`, `price`, `category_id`) VALUES
('HP / Ноутбук 15-db1027ur Ryzen 3-3200U/8Gb/1Tb/15.6\"HD/AMD Vega 3/Win10', 'Качество, производительность и длительное время работы от аккумулятора для решения повседневных задач. Дисплей диагональю 39,6 см (15,6\"\") с антибликовым покрытием и белой светодиодной подсветкой выглядит так же потрясающе, как и работает. Благодаря процессору AMD и графическому адаптеру этот ноутбук предоставляет возможности для просмотра веб-страниц, трансляции видео и выполнения множества других задач. Вам больше не придется ждать зарядки ноутбука несколько часов. Аккумулятор этого ноутбука можно зарядить с 0 до 50 % всего за 45 минут. Проводите время с удовольствием и оставайтесь на связи до 10 часов благодаря аккумулятору с технологией быстрой зарядки HP Fast Charge. Кроме того, накопитель большого объема позволяет с легкостью сохранять любимые фильмы, фото и музыку.', '35990', '1'),
('Pantum / Монохромный лазерный принтер P2207', 'Монохромный лазерный принтер. Компактный дизайн, низкая стоимость печати и оригинальные заправочные комплекты.', '5095', '2'),
('Polaris / Робот-пылесос PVCR 0726W', 'В комплектации с роботом-пылесосом представлены контейнер для пыли, резервуар для воды, салфетка из микрофибры, 2 боковых щеточки, 2 фильтра, АКБ и зарядная станция. АКБ емкостью 2600 mAh обеспечивает работу без подзарядки-200 мин. На полную зарядку понадобится не более 5 часов. Высота робота-пылесоса всего 7,6 см, а диаметр 31 см. Размер пылесоса позволит ему проникать под мебель, куда не всегда доберется обычный пылесос. Для ориентации в пространстве прибор оборудован инфракрасными датчиками. Он свободно объезжает мебель и другие препятствия, а не упасть с лестницы ему помогают датчики антипадения. Небольшие изменения высоты, например, пороги или переход с пола на ковер, прибор легко преодолевает без каких-либо повреждений, так как его колеса имеют амортизаторы. Для влажной уборки контейнер для пыли нужно заменить на резервуар с водой, а на нижнюю поверхность пылесоса прикрепить салфетку. С помощью пульта ДУ можно установить текущее время и задать время начала ежедневной уборки.', '17999', '3'),
('Polaris / Мясорубка PMG 3044 ProGear Inside', 'Мощность мясорубки составляет 3000Вт, что позволяет приготовить до 3кг фарша в минуту, отличается не только высокой мощностью, но и надежностью. Редуктор особой конструкции гарантирует долгую службу устройства за счет износостойкой стальной шестерни. Двигатель мясорубки защищен от перегрузок и перепадов электроэнергии благодаря технологии PROtect+ при опасности перегрева мясорубка автоматически отключается. Мясорубка оснащена функцией реверса-обратная прокрутка незаменима, когда шнек забивается жилами и мясными волокнами, замедляя работу мясорубки. В комплекте есть все необходимое для создания как оригинального праздничного ужина, так и обедов на каждый день-три стальные сетки (3,5,7мм), металлический мясоприемник, пластиковый толкатель для продуктов и две насадки-для приготовления колбас и кеббе. Все детали можно хранить в специальном отсеке внутри прибора. Компактная и стильная, не займет много места, а нескользящие ножки надежно зафиксируют ее на любой рабочей поверхности.', '9999', '3'),
('Bosch / Кофемолка MKM 6000/6003', 'Очень удобная в повседневной жизни кофемолка. Она предназначена для семей, которые любят выпить по утрам чашечку свежесваренного кофе. Производительность: до 75 гр. кофейных зерен. Степень помола зависит от его продолжительности: чем дольше, тем мельче. Мощность: 180 Вт. Лезвие из нержавеющей стали для точного и качественного помола. Помол осуществляется только при закрытой крышке.', '1431', '3'),
('Karcher / Минимойка K 5 basic', 'Модель мойки высокого давления K 5 basic это базовый аппарат, который справляется со стойкими загрязнениями и подходит как для очистки велосипедов или автомобилей так и для фасадов зданий, дорожек и прилегающей территории. При регистрации техники на сайте производителя Karcher срок гарантии увеличивается до 10 лет.', '22490', '3'),
('FINISH / Таблетки для мытья посуды в посудомоечной машине 65 шт', 'кислородсодержащие отбеливатели 100%', '1086', '3'),
('CROCS / Сандалии', 'текстиль 100%', '2309', '4'),
('Филипок и Ко / Скорочтение для детей от 6 до 9 лет.', '"Скорочтение. Как научить ребенка быстро читать" - переработанное и усовершенствованное переиздание успешного бестселлера по методике скорочтения Ахмадуллина, разделенное на две книги по возрасту для более точечной проработки навыков. Шамиль Ахмадуллин - психолог-педагог, физик, PhD, разработчик более 40 методик эффективного обучения детей. Основатель школ скорочтения и развития памяти у детей. За 18 дней 20-30 минутных занятий Ваш ребенок научится читать в 2 раза быстрее, лучше понимать, запоминать и пересказывать прочитанное. А главное - вы заложите в ребенка один из самых важных навыков 21 века - быстрое усвоение любой текстовой информации. Это даст ребенку огромные преимущества во взрослой жизни! Книга-тренинг для младших школьников: 18-дневный тренинг; более 100 заданий; улучшение внимания; повысится успеваемость в школе; ребенок научится пересказывать; тренировка памяти; формирование навыка быстрого чтения; занятия по 30 минут в день.', '979', '6'),
('Зверье Моё / Когтеточка-столбик "Зверьё Моё" с полкой, джут, крем-брюле, 40*40*60 см', 'Многофункциональный комплекс Зверье Мое: поточить, поиграть, полежать. Преимущества: - Джут - натуральное текстильное волокно, изготавливаемое из растений одноимённого рода; - серхняя полочка с бортиком, обтянутая премиальным мехом, подарит чудесные минуты отдыха; - пропитка - это наше собственное ноу-хау, неуловимое для человека и притягательное для кошки; - подвесная игрушка не оставит равнодушным питомца; в связи с отзывами о том, что шарик быстро отрывается, подвесную игрушку сделали из джута; - сборка за 20 секунд без инструментов и дополнительных деталей. Когтеточка-столбик "Зверье Мое" поможет сохранить мебель и ковры в доме в целостности. Во время царапания кошка выполняет сразу три жизненно важных процесса: стачивает отросшие когти, одновременно затачивая их, метит территорию и выполняет гимнастику тела.', '904', '7'),
('ТВОЕ / Платье', 'Размер на модели: S. *Параметры в описании указаны для размера: S.', '999', '5');
