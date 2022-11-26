import axios from 'axios'

const URL = process.env.VUE_APP_RUTA_BACKEND

export const employeeService = {
    getEmployeesAct,
    terminateEmployee,
    addEmployee,
    getStatusEmp,
    searchProjectOfEmp,
    updateEmployee,
    searchUnsuscribe,
    deleteEmployee,
    getAll,
    backEmployee

}

function getAll(){
    return axios.get(`${URL}api/empleados/lista`,{
        headers: {
                    'Authorization': 'Bearer '+localStorage.getItem('token')
                 }
            })
            .then( response => {
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function getEmployeesAct(){
    
   return  axios.get(`${URL}api/empleados/activos`,{
        headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token')
                }
        })
        .then(response => {
            console.log(response.data)
            return response.data     
        })
        .catch(e => {
            console.log(e)    
        })
    
}

function getStatusEmp(idPro){

    return axios({
        method: 'get',
        url: `${URL}api/empleados/status`,
        params: {
            idProyecto: idPro
                },
        headers: {
            'Authorization': 'Bearer '+localStorage.getItem('token')
                }
            })
            .then(response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function terminateEmployee(idEmpleado){
    
    return axios({
                method: "post",
                url: `${URL}api/empleados/baja`,
                params: {
                    idEmpleado: idEmpleado
                    },
                headers: {
                    'Authorization': 'Bearer '+localStorage.getItem('token')
                }
                })
                .then(response => {
                    response.data
                })
                .catch(e => {
                    console.log(e)
                })
}

function addEmployee(empleado){

    return axios.post(`${URL}api/empleados/agregar`, empleado, {
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token')
                }
            })
            .then(response => {
                    response.data
            })
            .catch(e => {
                    console.log(e)
            })
}

function searchProjectOfEmp(idEmpleado){
    return axios({
            method: "get",
            url: `${URL}api/proyectos/verificar`,
            params: { 
                idEmpleado : idEmpleado
                },
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token')
            }
            })
            .then(response => {
                console.log(response.data)
                return response.data
            })
            .catch(e =>{
                console.log(e)
            })
}

function updateEmployee(data){
    return axios.post(`${URL}api/empleados/update`, data, {
        headers: {
            'Authorization': 'Bearer '+localStorage.getItem('token')
         }
        })
        .then(response => {
            return response.data
        })
        .catch(e => {
            console.log(e)
        })
}

function searchUnsuscribe(){
    return axios.get(`${URL}api/empleados/inactivos`, {
            headers: { 
                'Authorization': 'Bearer '+localStorage.getItem('token')
                }
            })
            .then( response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function deleteEmployee(idEmpleado){
    
    return axios({
            method: 'delete',
            url: `${URL}api/empleados/borrar`,
            params: {
                idEmpleado: idEmpleado
             },
             headers: {
                'Authorization': 'Bearer '+localStorage.getItem('token')
             }
            })
            .then( response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function backEmployee(idEmpleado){
    return axios({
                method: "post",
                url: `${URL}api/empleados/volverAlta`,
                params: {
                    idEmpleado: idEmpleado 
                },
                headers: {
                    'Authorization': 'Bearer '+localStorage.getItem('token')
                }
             })
            .then( response => {
                response.data
            })
            .catch(e => {
                console.log(e)
            })
}