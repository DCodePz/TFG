/*!
    * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2023 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
//
// Scripts
//

window.addEventListener('DOMContentLoaded', event => {
    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }
});

// $(document).ready(function() {
//     // Inicializar CKEditor cuando se carga por primera vez
//     ClassicEditor
//         .create($('#editor')[0])
//         .catch(error => {
//             console.error(error);
//         });
//
//     // Re-inicializar CKEditor cuando HTMX actualiza el contenido dinámicamente
//     $(document).on('htmx:afterSwap', function(event) {
//         // Si el contenedor con el editor ha sido actualizado
//         if ($(event.target).is('#editor-container')) {
//             ClassicEditor
//                 .create($('#editor')[0])  // Volver a inicializar el editor
//                 .catch(error => {
//                     console.error(error);
//                 });
//         }
//     });
// });
