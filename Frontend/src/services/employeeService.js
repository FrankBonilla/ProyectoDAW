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
    return axios.get(`${URL}api/empleados/lista`)
            .then( response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function getEmployeesAct(){
    
   return axios.get(`${URL}api/empleados/activos`)
        .then(response => {
            console.log(response.data)
            return response.data
            
        })
        .catch(e => {
            console.log(e)
        })
    
}

function getStatusEmp(idPro){

    return axios.post(`${URL}api/empleados/status`,idPro)
            .then(response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function terminateEmployee(id){
    
    return axios.post(`${URL}api/empleados/baja/${id}`)
                .then(response => {
                    response.data
                })
                .catch(e => {
                    console.log(e)
                })
}

function addEmployee(data){

    return axios.post(`${URL}api/empleados/agregar`, data)
                .then(response => {
                    response.data
                })
                .catch(e => {
                    console.log(e)
                })
}

function searchProjectOfEmp(data){

    return axios.post(`${URL}proyectos/verificar`, data)
            .then(response => {
                console.log(response.data)
                return response.data
            })
            .catch(e =>{
                console.log(e)
            })
}

function updateEmployee(data){
    return axios.post(`${URL}api/empleados/update`, data)
        .then(response => {
            return response.data
        })
        .catch(e => {
            console.log(e)
        })
}

function searchUnsuscribe(){
    return axios.get(`${URL}api/empleados/inactivos`)
            .then( response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function deleteEmployee(id){
    return axios.delete(`${URL}api/empleados/borrar/${id}`)
            .then( response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function backEmployee(data){
    return axios.post(`${URL}api/empleados/volver-alta`, data)
            .then( response => {
                response.data
            })
            .catch(e => {
                console.log(e)
            })
}