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
    return axios.get(`${URL}api/proyectos/lista`, {
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

function getProjectsAct(){

    return axios.get(`${URL}api/proyectos/activos`, {
        headers: {
            'Authorization': 'Bearer '+localStorage.getItem('token')
            }
        })
        .then(response => {
            console.table(response.data)
            return response.data
            
        })
        .catch(e => {
            console.log(e)
        })
}

function saveProyect(datos){

    return axios.post(`${URL}api/proyectos/guardar`,datos, {
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

function deleteProject(idProyecto){
    return axios({
            method: 'delete',
            url: `${URL}api/proyectos/borrar`,
            params: {
                idProyecto: idProyecto
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

function asignarEmpleado(idProyecto,idEmpleado){
    return axios({
                method: 'post',
                url: `${URL}api/proyectos/asignarEmpleado`,
                params: {
                    idProyecto: idProyecto,
                    idEmpleado: idEmpleado
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

function removeEmployee(idProyecto,idEmpleado){
    return axios({
        method: 'post',
        url: `${URL}api/proyectos/removeEmployee`,
        params: {
            idProyecto: idProyecto,
            idEmpleado: idEmpleado
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

function searchEmpInProjects(idProyecto){
    return axios({
        method: 'get',
        url: `${URL}api/proyectos/verificarPro`,
        params: {
            idProyecto: idProyecto
                },
        headers: {
            'Authorization': 'Bearer '+localStorage.getItem('token')
                    }
            })
            .then(response => {
                return response.data
            })
            .catch(e => {
                console.log.log(e)
            })
}

function terminateProject(idProyecto){
    return axios({
            method: 'post',
            url: `${URL}api/proyectos/baja`,
            params: {
                idProyecto: idProyecto
                },
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

function updateProject(data){
    return axios.post(`${URL}api/proyectos/update`, data, {
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

function unsuscribeProyect(){
    return axios.get(`${URL}api/proyectos/inactivos`, {
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