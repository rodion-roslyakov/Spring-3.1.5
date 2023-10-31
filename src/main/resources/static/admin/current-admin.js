const URLNavbarAdmin = 'http://localhost:8080/admin/api/showAccount/';
const navbarBrandAdmin = document.getElementById('navbarBrandAdmin');//Элемент, где будет роль и почта текущего юзера
const tableUserAdmin = document.getElementById('tableAdmin');//Элемент, где будет таблица текущего юзера

function getCurrentAdmin() {
    fetch(URLNavbarAdmin)
        .then((res) => res.json())
        .then((userAdmin) => {

            let rolesStringAdmin = rolesToStringForAdmin(userAdmin.roles);
            let data = '';

            data += `<tr>
            <td>${userAdmin.id}</td>
            <td>${userAdmin.firstName}</td>
            <td>${userAdmin.secondName}</td>
            <td>${userAdmin.age}</td>
            <td>${userAdmin.username}</td>
            <td>${rolesStringAdmin}</td>
            </tr>`;
            tableUserAdmin.innerHTML = data;
            navbarBrandAdmin.innerHTML = `<b><span>${userAdmin.username}</span></b>
                             <span>with roles:</span>
                             <span>${rolesStringAdmin}</span>`;
        });
}

getCurrentAdmin()

function rolesToStringForAdmin(roles) {
    let rolesString = '';

    for (const element of roles) {
        rolesString += (element.role.toString().replace('ROLE_', ' '));
    }
    return rolesString;
}

