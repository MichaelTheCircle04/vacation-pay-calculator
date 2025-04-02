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

        if (resp.status === 200) {
            return resp.json(); // Возвращаем промис
        } else {
            return Promise.reject(resp.json()); // Возвращаем промис с ошибкой
        }
    })
    .then(resolved => {

        document.getElementById("message").innerHTML = "Calculated";
        document.getElementById("message").style = "color: green";
        
        let amount = document.createElement("span");
        amount.innerHTML = "Сумма ваших отпускных: " + resolved.totalAmount;
        document.body.appendChild(amount); 
        document.body.appendChild(document.createElement("br"));
        
        if (resolved.holidays.length !== 0) {

            let txt = document.createElement("span");
            txt.innerHTML = "Праздничные дни в период вашего отпуска:";
            document.body.appendChild(txt);
            document.body.appendChild(document.createElement("br"));

            let table = document.createElement("table");
        
            resolved.holidays.forEach(h => { 
                let r = table.insertRow();
                let c1 = r.insertCell();
                let c2 = r.insertCell();
                c1.innerHTML = h.date; 
                c2.innerHTML = h.nameHoliday; 
            });
        
            document.body.appendChild(table); // Добавляем таблицу на страницу
        }
    }, rejected => {
        rejected.then(errorData => { // Обрабатываем ошибку
            document.getElementById("message").innerHTML = errorData.exception;
            document.getElementById("message").style = "color: red";
            cleanForm();
        });
    });
}