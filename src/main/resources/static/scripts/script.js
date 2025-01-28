let properties_get = ["get_name"];

const xhttp = new XMLHttpRequest();
xhttp.onload = function () {
    let text = "<img src='" + this.responseURL + "' alt='user' width='25' height='25'>";
    text += "Name: ";
    document.getElementById("img1").innerHTML = text;
}
xhttp.open("GET", "/placeholder.png");
xhttp.send();
function loadGetMsg() {
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        document.getElementById("getrespmsg").innerHTML =
            this.responseText;
    }
    let query = "";
    for (let i = 0; i < properties_get.length; i++) {
        let element = document.getElementById(properties_get[i]);
        let value = element.value;
        let propName = properties_get[i].substring(4);
        if (value != "") {
            query += propName + "=" + encodeURIComponent(value) + "&";
        }

    }
    console.log(query);
    xhttp.open("GET", "/app/getObjectFromQuery?" + query);
    xhttp.send();
}

function loadPostMsg(name) {
    let url = "/app/getObjectFromQuery?name=" + encodeURIComponent(name.value);
    fetch(url, { method: 'POST' })
        .then(x => x.text())
        .then(y => document.getElementById("postrespmsg").innerHTML = y)
        .catch(e => {
            console.log(e);
            console.log("Error");
            console.log(url);
        });
}

function addProperty() {
    // Ask for the property name
    let propName = prompt("Please enter the property name", "Age");
    if (propName == null || propName == "") {
        alert("Property name must be filled out");
        return;
    }
    propName = propName.toLowerCase();
    let element = document.createElement("div");
    element.className = "flex-row";
    let label = document.createElement("label");
    let firstLetter = propName.charAt(0).toUpperCase();
    let propNameT = firstLetter + propName.slice(1);
    label.htmlFor = "get_" + propName;;
    label.innerHTML = propNameT + ": ";
    element.appendChild(label);
    // Create a new input element
    let input = document.createElement("input");
    input.type = "text";
    input.name = "get_" + propNameT;
    input.id = "get_" + propName;
    element.appendChild(input);
    document.getElementById("form-get").appendChild(element);
    properties_get.push("get_" + propName);
}