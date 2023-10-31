const URLNavbarUser = 'http://localhost:8080/user/api/showAccount/';
const navbarBrandUser = document.getElementById('navbarBrandUser'); //Элемент, где будет роль и почта текущего юзера
const tableUserUser = document.getElementById('tableUser');//Элемент, где будет таблица текущего юзера

function getCurrentUser() {
    fetch(URLNavbarUser)
        .then((res) => res.json())
        .then((user) => {

            let rolesStringUser = rolesToStringForUser(user.roles);
            let dataOfUser = '';

            dataOfUser += `<tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.secondName}</td>
            <td>${user.age}</td>
            <td>${user.username}</td>
            <td>${rolesStringUser}</td>
            </tr>`;
            tableUserUser.innerHTML = dataOfUser;
            navbarBrandUser.innerHTML = `<b><span>${user.username}</span></b>
                             <span>with roles:</span>
                             <span>${rolesStringUser}</span>`;
        });
}

getCurrentUser();

function rolesToStringForUser(roles) {
    let rolesString = '';
    for (let element of roles) {
        rolesString += (element.role.toString().replace('ROLE_', '') + ' ');
    }
    return rolesString;
}