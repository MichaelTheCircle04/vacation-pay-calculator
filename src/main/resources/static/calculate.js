function reload() {
    location.reload();
}

function cleanForm() {

    let form = document.getElementById("information");
    form.elements.firstDay.value = "";
    form.elements.lastDay.value = "";
}

function submitForm() {

    let averageSalary = document.getElementById("averageSalary").value;
    let numberOfDays = document.getElementById("numberOfDays").value;
    let firstDay = document.getElementById("firstDay").value;
    let lastDay = document.getElementById("lastDay").value;

    let data = {
        averageSalary: averageSalary,
        numberOfDays: numberOfDays,
        firstDay: firstDay,
        lastDay: lastDay
    }

    headers = {"Content-type": "application/json"};
    let jsonData = JSON.stringify(data);

    fetch("http://localhost:8080/calculate", {
        method: "POST",
        headers: headers,
        body: jsonData
    })
    .then(resp => {
        console.log(resp);
        if (resp.status === 200) {
            return resp.json(); // Возвращаем промис
        } else {
            return Promise.reject(resp.json()); // Возвращаем промис с ошибкой
        }
    })
    .then(resolved => {
        console.log(resolved);
        document.getElementById("message").innerHTML = "Calculated";
        document.getElementById("message").style = "color: green";
        
        let amount = document.createElement("span"); // Изменил на "div" вместо "amount"
        amount.innerHTML = "Сумма ваших отпускных: " + resolved.totalAmount;
        document.body.appendChild(amount); // Добавляем элемент на страницу
        
        //написать про праздничные дни
        let table = document.createElement("table"); // Изменил на "table" вместо "result"
        
        resolved.holidays.forEach(h => { // Используем forEach для перебора массива
            let r = table.insertRow();
            let c1 = r.insertCell();
            let c2 = r.insertCell();
            c1.innerHTML = h.date; // Исправлено на h.date
            c2.innerHTML = h.nameHoliday; // Исправлено на h.nameHoliday
        });
    
        document.body.appendChild(table); // Добавляем таблицу на страницу
    
    }, rejected => {
        rejected.then(errorData => { // Обрабатываем ошибку
            document.getElementById("message").innerHTML = errorData.exception;
            document.getElementById("message").style = "color: red";
            cleanForm();
        });
    });
}