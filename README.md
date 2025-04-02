# vacation-pay-calculator

### Цели:
- Получить практический опыт работы со Spring MVC
- Улучшить навык написания тестов

### Технологии:
- Spring MVC
- Spring Test

### Концепция:
Работу над этим проектом я начал довольно давно, когда пытался попасть на стижировку в какую-то компанию и там это
было указано в качестве практического задания. На тот момент это был мой первый опыт использования Spring MVC.
Я кое-как написал этот проект, выложил на GitHub (это было первое, что я туда залил) и забыл.
Со временем, когда я начал рефакторить свои старые проекты, я наткнулся на него. С учетом накопившегося опыта
я решил почти полностью его переписать. Да, ничему новому в процессе работы над ним я не научился, но тем не менее 
это все-таки дополнительная практика, которая никогда не повредит.

Приложение представляет из себя простой калькулятор отпускных, при этом рассчет можно осуществить предоставив 
либо колличество дней в отпуске, либо точные даты начала и окончания отпуска.
Доступ к приложению осуществляется через фронт, который представляет из себя html-страничку и js-код, на стороне
сервера проводится проверка корректности предоставленных данных (дата начала должна быть раньше даты окончания,
если предоставлена одна дата, то вторая должна быть также предоставлена, если даты не предоставлены, то необходимо 
предоставить в свою очередь колличество дней, также важно отметить, что при условии предоставления дат, колличество дней,
пускай оно и указано, в расчет не берется). Дальше осуществляется рассчет. При условии, что даты предоставлены, из общего
колличества дней вычитаются праздники, соотвественно сумма отпускных меняется, помимо этого пользователю выводятся даты и 
названия праздников которые будут в период его отпуска. Если даты не предоставлены, рассчет осуществляется просто по колличеству
дней.

### Компоненты:
- CalculatorController - простой контроллер с двумя маппингами (GET /calculate и POST /calculate)
- VacationInfoValidator - класс с одним статическим методом, которые проверяет корректность объекта 
VacationInfo и выбрасывает исключение IllegalArgumentException, если объект некорректный
- MainAdvice - обрабатывает исключение IllegalArgumentException и возвращает ответ со статусом 400 и текстом ошибки
- Holiday - класс представляющий праздничный день, содержит два поля: дату и название праздника, а также: 
    - статическое поле HOLIDAY_COMPARATOR
    - статический метод holidaysBetween(LocalDate firstDay, LocalDate lastDay, List<Holiday> holidays),
    который с помощью алгоритма бинарного поиска находит в предоставленном (отсортированном!) списке первый праздничный день,
    который больше параметра firstDay или равен ему, а впоследствии создает список из всех праздничных дней которые находятся между
    параметрами firstDay и lastDay и возвращает его 
- VacationInfo - класс представляющий информацию об отпуске (даты, кол-во дней, среднюю З/П)
- CalculationsDTO - DTO для передачи результатов вычислений, содержит информацию о сумме отпускных и список праздничных дней 
- HolidayCalendar - содержит отсортированный список объектов Holiday, позволяет добавлять туда новые объекты, а также имеет метод 
calculateWorkinDays который меняет значение поля numberOfDays объект VacationInfo и возвращает List<Holiday>
- Calculator - подготавливает и возвращает объект CalculationsDTO 
- CalculatorControllerTest - интеграционный тест для проверки работы приложения в случае получения корректных и некорректных запросов
