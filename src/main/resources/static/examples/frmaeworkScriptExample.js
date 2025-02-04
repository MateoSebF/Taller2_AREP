let properties_post = ["post_name"];

const xhttp = new XMLHttpRequest();
xhttp.onload = function () {
    let text = "<img src='" + this.responseURL + "' alt='user' width='25' height='25'>";
    text += "Name: ";
    document.getElementById("img1").innerHTML = text;
    document.getElementById("img4").innerHTML = text;
}
xhttp.open("GET", "/placeholder.png");
xhttp.send();


function getAObject() {
    const xhttp = new XMLHttpRequest();
    let query = encodeURIComponent(document.getElementById("get_name").value);
    xhttp.onload = function () {
        document.getElementById("get_result").innerHTML =
            this.responseText;
    }
    xhttp.open("GET", "/app/objectFromQuery?name=" + query);
    xhttp.send().catch(e => {
        document.getElementById("getObjectDetails").innerHTML =
            e;
    });
}

function postAObject() {
    let url = "/app/objectFromQuery";
    let query = "";
    for (let i = 0; i < properties_post.length; i++) {
        console.log(properties_post[i]);
        let element = document.getElementById(properties_post[i]);
        let value = element.value;
        let propName = properties_post[i].substring(5);
        if (value != "") {
            query += propName + "=" + encodeURIComponent(value) + "&";
        }

    }
    query = query.substring(0, query.length - 1);
    url += "?" + query;
    fetch(url, { method: 'POST' })
        .then(x => x.text())
        .then(y => document.getElementById("post_result").innerHTML = y)
        .catch(e => {
            console.log(e);
            console.log("Error");
            console.log(url);
        });
}

function addProperty() {
    let propName = prompt("Please enter some property name", "");
    if (propName == null || propName == "") {
        alert("Property name must be filled out");
        return;
    }
    propName = encodeURIComponent(propName.toLowerCase());
    let firstLetter = propName.charAt(0).toUpperCase();
    let propNameT = firstLetter + propName.slice(1);

    let element = document.createElement("div");
    element.className = "property";

    let label = document.createElement("label");
    label.htmlFor = "post_" + propName;
    label.innerHTML = propNameT + ": ";
    element.appendChild(label);

    let input = document.createElement("input");
    input.type = "text";
    input.name = "post_" + propName;
    input.id = "post_" + propName;
    element.appendChild(input);
    
    document.getElementById("post_data").appendChild(element);
    properties_post.push("post_" + propName);
}

function showContent(method) {
    // Hide all direct divs with the class form
    const divs = document.querySelectorAll('.form-content > div');
    divs.forEach(div => div.style.display = 'none');

    // Show the selected div
    const selectedDiv = document.querySelector(`.${method}_form`);
    if (selectedDiv) {
        selectedDiv.style.display = 'flex';
    }
}
