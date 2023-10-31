async function getUserById(id) {
    let response = await fetch("http://localhost:8080/admin/api/users/" + id);
    return await response.json();
}

async function open_fill_modal(form, modal, id) {
    modal.show();
    let user = await getUserById(id);
    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.secondName.value = user.secondName;
    form.age.value = user.age;
    form.username.value = user.username;
    form.password.value = user.password;
    user.roles.forEach(role => {
        formEdit.roles.options[role.id-1].selected = true
    });
}