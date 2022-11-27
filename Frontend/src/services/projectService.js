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
    deleteProject,
    exportToExcell,
    exportProjectToExcell
    
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

function exportToExcell(){
    return axios({
        method: 'get',
        url: `${URL}api/proyectos/exportar/excel/proyectos/activos`,
        headers: {
            'Authorization': 'Bearer '+localStorage.getItem('token')
        },
        responseType: 'blob'
    })
    .then(response => {
        const today = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
        console.log(response.data)
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download',`Proyectos de activos ${today.split("-").join("_")}.xlsx`)
        document.body.appendChild(link)
        link.click()
    })
    .catch(e => {
        console.log(e)
    })
}

function exportProjectToExcell(idProyecto){
    return axios({
        method: 'get',
        url: `${URL}api/proyectos/exportar/excel/proyecto`,
        params: {
            idProyecto: idProyecto
        },
        headers: {
            'Authorization': 'Bearer '+localStorage.getItem('token')
        },
        responseType: 'blob'
    })
    .then(response => {
        const today = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
        console.log(response.data)
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download',`Informe de proyecto ${today.split("-").join("_")}.xlsx`)
        document.body.appendChild(link)
        link.click()
    })
    .catch(e => {
        console.log(e)
    })
}