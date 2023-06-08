import axios from 'axios'

export const host = "http://localhost:8080/api"

//Llamada a la API genérica, parametrizada con method, body y path
export function llamadaAPI(method, body, path) {
  let config = {
      method: method ?? 'get',
      maxBodyLength: Infinity,
      url: path,
      headers: {}
  }

  if (body) {
      config.data = body,
        config.headers['Content-Type'] = 'application/json'
  }

  //console.log('path = ', path)
  return axios.request(config)
}

// PUT
function llamadaApiPUTParams(path, params = {}) {
  let config = {
      method: 'put',
      maxBodyLength: Infinity,
      url: path,
      headers: {},
      params: params // Añadimos los parámetros a la llamada
  };
  return axios.request(config);
}

// GETS
export function getEntidades(nombre) {
  //return llamadaAPI('get',null,`${host}api/${nombre}`)
  return llamadaAPI('get', null, `${host}/${nombre}`)
}

function getEntidadPorId(nombre, id) {
  //console.log("recuperando entidad", nombre, "con id", id)
  return llamadaAPI('get', null, `${host}/${nombre}/${id}`)
}

function getEntidadPorNombre(nombre, campo, valor) {
  return llamadaAPI('get', null, `${host}/${nombre}/${campo}/${valor}`)
}

//POST
function postEntidad(modelo, nombre) {
  return llamadaAPI('post', modelo, `${host}/${nombre}`)
}

//PUT
function putEntidad(modelo, id, nombre) {
  return llamadaAPI('put', modelo, `${host}/${nombre}/${id}`)
}

//PATCH
function patchEntidad(modelo, entidad, id, nombre) {
  return llamadaAPI('patch', modelo, `${host}/${entidad}/${id}/${nombre}`)
}

// DELETE
function deleteEntidad(id, nombre) {
  return llamadaAPI('delete', null, `${host}/${nombre}/${id}`)
}



/////******************* visitas ****************/////////

export function getVisitas() {
  return getEntidades('visitas')
}

export function getVisitaPorId(id) {
  return getEntidadPorId('visitas', id)
}

export function postVisita(visita) {
  return postEntidad(visita, 'visitas')
}

export function putVisita(visita) {
  //console.log("llamando a put ", categoria, categoria.id)
  return putEntidad(visita, visita.id, 'visitas')
}

export function deleteVisita(visita) {
  //console.log("llamando a delete ", categoria, categoria.id)
  return deleteEntidad(visita.id, 'visitas')
}

/// POST INVITADOS A VISITA
export function addInvitadosToVisita(listaInvitados, id) {
   return llamadaAPI('post', listaInvitados, `${host}/visitas/${id}/invitados`)
}
////////////////////////////

/// GET INVITADOS DE VISITA
export function getInvitadosVisita(id) {
  console.log("getInvitadosVisita dentro del api-service")
  return llamadaAPI('get', null, `${host}/visitas/${id}/invitados`)
}
////////////////////////////



///// personas

export function getPersonas(tipo) {
  return getEntidades('personas?tipo='+tipo)
}

export function getPersonaPorId(id) {
  return getEntidadPorId('personas', id)
}

export function postPersona(persona) {
  return postEntidad(persona, 'personas')
}

export function putPersona(persona) {
  return putEntidad(persona, persona.id, 'personas')
}

export function deletePersona(persona) {
  return deleteEntidad(persona.id, 'personas')
}

/// GET VISITAS DE PERSONA
export function getVisitasPersona(id) {
  return llamadaAPI('get', null, `${host}/personas/${id}/visitas`)
}
////////////////////////////


