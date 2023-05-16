// export function dateToString(fecha) {
//     return fecha.toLocaleDateString('es-ES', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })
//                  + ' a las ' + fecha.toLocaleTimeString('es-ES', { hour: "2-digit", minute: "2-digit" })
// }

export function timestampToFecha(fecha) {
    let options = { year: 'numeric', month: 'long', day: 'numeric' }
    return fecha.toLocaleDateString('es-ES', options)            
}

export function timestampToHora(fecha) {
    let options = { hour: "2-digit", minute: "2-digit" }
    return fecha.toLocaleTimeString('es-ES', options)
}

