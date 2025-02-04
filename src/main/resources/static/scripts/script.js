let properties_post = ["post_name"];
let properties_put = ["put_name"];

const xhttp = new XMLHttpRequest();
xhttp.onload = function () {
    let text = "<img src='" + this.responseURL + "' alt='user' width='25' height='25'>";
    text += "Name: ";
    document.getElementById("img1").innerHTML = text;
    document.getElementById("img4").innerHTML = text;
}
xhttp.open("GET", "/images/placeholder.png");
xhttp.send();


function getAObject() {
    const xhttp = new XMLHttpRequest();
    let response = document.createElement("div");
    let title = document.createElement("h3");
    response.appendChild(title);

    let query = encodeURIComponent(document.getElementById("get_name").value);

    xhttp.onload = function () {
        // Check the status code
        if (this.status >= 200 && this.status < 300) {
            title.innerHTML = `✅ The GET request was successful! (Status: ${this.status})`;
            let text = document.createElement("p");
            text.innerHTML = decodeURIComponent(this.responseText);
            response.appendChild(text);
        } else {
            title.innerHTML = `❌ The GET request failed! (Status: ${this.status})`;
        }

        document.getElementById("get_response").innerHTML = response.innerHTML;
    };

    xhttp.onerror = function () {
        title.innerHTML = "❌ Network Error - Request failed!";
        response.appendChild(title);
        document.getElementById("get_response").innerHTML = response.innerHTML;
    };

    xhttp.open("GET", "/app/objectFromQuery?name=" + query, true);
    xhttp.send();
}


function postAObject() {
    let response = document.createElement("div");
    let title = document.createElement("h3");
    response.appendChild(title);

    let url = "/app/objectFromQuery";
    let query = "";

    // Build query parameters from input fields
    for (let i = 0; i < properties_post.length; i++) {
        let element = document.getElementById(properties_post[i]);
        let value = element.value;
        let propName = properties_post[i].substring(5);
        if (value !== "") {
            query += propName + "=" + encodeURIComponent(value) + "&";
        }
    }

    query = query.substring(0, query.length - 1); // Remove last '&'
    url += "?" + query;

    // Perform the fetch request
    fetch(url, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error(`❌ The POST request failed! (Status: ${response.status} - ${response.statusText})`);
            }
            return response.text();
        })
        .then(responseText => {
            title.innerHTML = `✅ The POST request was successful! (Status: 200)`;
            let text = document.createElement("p");
            text.innerHTML = decodeURIComponent(responseText);
            response.appendChild(text);
            document.getElementById("post_response").innerHTML = response.innerHTML;
        })
        .catch(error => {
            title.innerHTML = error.message;
            response.appendChild(title);
            console.error(error);
            document.getElementById("post_response").innerHTML = response.innerHTML;
        });
}

function putAObject() {
    let response = document.createElement("div");
    let title = document.createElement("h3");
    response.appendChild(title);

    let url = "/app/objectFromQuery";
    let query = "";

    // Build query parameters from input fields
    for (let i = 0; i < properties_put.length; i++) {
        let element = document.getElementById(properties_put[i]);
        let value = element.value;
        let propName = properties_put[i].substring(4);
        if (value !== "") {
            query += propName + "=" + encodeURIComponent(value) + "&";
        }
    }

    query = query.substring(0, query.length - 1); // Remove last '&'
    url += "?" + query;

    // Perform the fetch request
    fetch(url, { method: 'PUT' })
        .then(response => {
            if (!response.ok) {
                throw new Error(`❌ The PUT request failed! (Status: ${response.status} - ${response.statusText})`);
            }
            return response.text();
        })
        .then(responseText => {
            title.innerHTML = `✅ The PUT request was successful! (Status: 200)`;
            let text = document.createElement("p");
            text.innerHTML = decodeURIComponent(responseText);
            response.appendChild(text);
            document.getElementById("put_response").innerHTML = response.innerHTML;
        })
        .catch(error => {
            title.innerHTML = error.message;
            response.appendChild(title);
            console.error(error);
            document.getElementById("put_response").innerHTML = response.innerHTML;
        });
}

function deleteAObject() {
    let response = document.createElement("div");
    let title = document.createElement("h3");
    response.appendChild(title);

    let query = encodeURIComponent(document.getElementById("get_name").value);
    let url = "/app/objectFromQuery?name=" + query;

    fetch(url, { method: 'DELETE' })
        .then(response => {
            if (!response.ok) {
                throw new Error(`❌ The DELETE request failed! (Status: ${response.status} - ${response.statusText})`);
            }
            return response.text();
        })
        .then(responseText => {
            title.innerHTML = `✅ The DELETE request was successful! (Status: 200)`;
            let text = document.createElement("p");
            text.innerHTML = decodeURIComponent(responseText);
            response.appendChild(text);
            document.getElementById("delete_response").innerHTML = response.innerHTML;
        })
        .catch(error => {
            title.innerHTML = error.message;
            response.appendChild(title);
            console.error(error);
            document.getElementById("delete_response").innerHTML = response.innerHTML;
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
    let div = document.createElement("div");
    div.innerHTML = propNameT + ": ";
    div.className = "name-align";
    label.appendChild(div);
    element.appendChild(label);

    let input = document.createElement("input");
    input.type = "text";
    input.name = "post_" + propName;
    input.id = "post_" + propName;
    element.appendChild(input);

    let button = document.createElement("button");
    button.innerHTML = "🗑️";
    button.class = "delete_button";
    button.type = "button";
    button.onclick = function () {
        element.remove();
        properties_post = properties_post.filter(e => e !== "post_" + propName);
    };
    element.appendChild(button);

    document.getElementById("post_data").appendChild(element);
    properties_post.push("post_" + propName);
}

function loadUser() {
    let response = document.createElement("div");
    response.id = "put_data_info";
    let title = document.createElement("h3");
    response.appendChild(title);

    let query = encodeURIComponent(document.getElementById("put_name").value);
    let url = "/app/objectFromQuery?name=" + query;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`❌ The LOAD of the user failed! (Status: ${response.status} - ${response.statusText})`);
            }
            return response.text();
        })
        .then(responseText => {
            title.innerHTML = `✅ The LOAD of the user was successful! (Status: 200)`;
            document.getElementById("put_response").innerHTML = response.innerHTML;
            loadAllProperties(responseText);
            document.getElementById("put_object").style.display = "block";
            document.getElementById("add_button_put").style.display = "block";
        })
        .catch(error => {
            title.innerHTML = error.message;
            response.appendChild(title);
            console.error(error);
            document.getElementById("put_response").innerHTML = response.innerHTML;
        });
}

function resetAllPut(){
    resetAllProperties();
    document.getElementById("add_button_put").style.display = "none";
    document.getElementById("put_object").style.display = "none";
    document.getElementById("put_response").innerHTML = "";
}

function resetAllProperties() {
    //clear the properties divs
    let properties = document.getElementById("put_data").querySelectorAll(".property");
    for (let i = 1; i < properties.length; i++) {
        properties[i].remove();
    }
    properties_put = ["put_name"];
}

function loadAllProperties(responseText) {
    resetAllProperties();   
    let response = JSON.parse(responseText);
    for (let key in response) {
        if (key !== "name") {
            loadPropertyPut(key, response[key]);
        }
    }
}

function loadPropertyPut(propName, value){
    let firstLetter = propName.charAt(0).toUpperCase();
    let propNameT = firstLetter + propName.slice(1);

    let element = document.createElement("div");
    element.className = "property";

    let label = document.createElement("label");
    label.htmlFor = "put_" + propName;
    let div = document.createElement("div");
    div.innerHTML = propNameT + ": ";
    div.className = "name-align";
    label.appendChild(div);
    element.appendChild(label);

    let input = document.createElement("input");
    input.type = "text";
    input.name = "put_" + propName;
    input.id = "put_" + propName;
    input.value = value;
    element.appendChild(input);

    let button = document.createElement("button");
    button.innerHTML = "🗑️";
    button.class = "delete_button";
    button.type = "button";
    button.onclick = function () {
        element.remove();
        properties_put = properties_put.filter(e => e !== "put_" + propName);
    };
    element.appendChild(button);

    document.getElementById("put_data").appendChild(element);
    properties_put.push("put_" + propName);
}

function addPropertyPut() {
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
    label.htmlFor = "put_" + propName;
    let div = document.createElement("div");
    div.innerHTML = propNameT + ": ";
    div.className = "name-align";
    label.appendChild(div);
    element.appendChild(label);

    let input = document.createElement("input");
    input.type = "text";
    input.name = "put_" + propName;
    input.id = "put_" + propName;
    element.appendChild(input);

    let button = document.createElement("button");
    button.innerHTML = "🗑️";
    button.class = "delete_button";
    button.type = "button";
    button.onclick = function () {
        element.remove();
        properties_put = properties_put.filter(e => e !== "put_" + propName);
    };
    element.appendChild(button);

    document.getElementById("put_data").appendChild(element);
    properties_put.push("put_" + propName);
}


function showContent(method) {
    // Hide all direct divs with the class form
    const divs = document.querySelectorAll('.form-content > div');
    divs.forEach(div => {
        div.style.display = 'none'
        let divResponse = div.querySelector(`.result-info`);
        divResponse.querySelector(`.result`).innerHTML = "";
    });

    // Show the selected div
    const selectedDiv = document.querySelector(`.${method}_form`);
    if (selectedDiv) {
        selectedDiv.style.display = 'flex';
    }
}
