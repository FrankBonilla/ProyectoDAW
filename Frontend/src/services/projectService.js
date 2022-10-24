import axios from 'axios'

const URL = process.env.VUE_APP_RUTA_BACKEND

export const projectService = {
    getProjectsAct,
    saveProyect,
    removeEmployee,
    asignarEmpleado,
    searchEmpInProjects,
    terminateProject,
    updateProject,
    unsuscribeProyect,
    getAll,
    deleteProject
    
}

function getAll(){
    return axios.get(`${URL}proyectos/lista`)
            .then(response => {
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function getProjectsAct(){

    return axios.get(`${URL}proyectos/activos`)
        .then(response => {
            console.table(response.data)
            return response.data
            
        })
        .catch(e => {
            console.log(e)
        })
}

function saveProyect(datos){

    return axios.post(`${URL}proyectos/guardar`,datos)
    .then(response => {
        console.log(response.data)
        return response.data
        
    })
    .catch(e => {
        console.log(e)
    })
}

function deleteProject(id){
    return axios.delete(`${URL}proyectos/borrar/${id}`)
            .then( response => {
                response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function asignarEmpleado(idProyecto,idEmpleado){
    return axios({
                method: 'post',
                url: `${URL}proyectos/asignarEmpleado`,
                params: {
                    idProyecto: idProyecto,
                    idEmpleado: idEmpleado
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

function removeEmployee(datos){
    return axios.post(`${URL}proyectos/remove-employee`,datos)
            .then(response => {
                console.log(response.data)
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function searchEmpInProjects(datos){
    return axios.post(`${URL}empleados/verificar`,datos)
            .then(response => {
                return response.data
            })
            .catch(e => {
                console.log.log(e)
            })
}

function terminateProject(id){
    return axios.post(`${URL}proyectos/baja/${id}`)
            .then( response => {
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function updateProject(data){
    return axios.post(`${URL}proyectos/update`, data)
            .then( response => {
                return response.data
            })
            .catch(e => {
                console.log(e)
            })
}

function unsuscribeProyect(){
    return axios.get(`${URL}proyectos/inactivos`)
        .then(response => {
            console.log(response.data)
            return response.data
        })
        .catch(e => {
            console.log(e)
        })
    
}