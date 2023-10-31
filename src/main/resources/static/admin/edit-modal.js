let formEdit = document.forms["formEdit"];
editUser();

const URLEdit = "http://localhost:8080/admin/api/users/";

async function editModal(id) {
    loadRolesForEdit();
    const modalEdit = new bootstrap.Modal(document.querySelector('#editModal'));
    await open_fill_modal(formEdit, modalEdit, id);
}
function editUser() {
    formEdit.addEventListener("submit", ev => {
        ev.preventDefault();

        //Приведение ролей из вида js к виду java
        let rolesForEdit = [];
        for (let i = 0; i < formEdit.roles.options.length; i++) {
            if (formEdit.roles.options[i].selected) rolesForEdit.push({
                id: formEdit.roles.options[i].value,
                role: "ROLE_" + formEdit.roles.options[i].text
            });
        }

        fetch(URLEdit + formEdit.id.value, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: formEdit.id.value,
                username: formEdit.username.value,
                password: formEdit.password.value,
                firstName: formEdit.firstName.value,
                secondName: formEdit.secondName.value,
                age: formEdit.age.value,
                roles: rolesForEdit
            })
        }).then(() => {
            $('#editClose').click();
            getAllUsers();
        });
    });
}

//Приведение ролей к виду JS
function loadRolesForEdit() {
    let selectEdit = document.getElementById("edit-roles");
    selectEdit.innerHTML = "";

    fetch("http://localhost:8080/admin/api/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.role.toString().replace('ROLE_', '');
                selectEdit.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}
window.addEventListener("load", loadRolesForEdit);

