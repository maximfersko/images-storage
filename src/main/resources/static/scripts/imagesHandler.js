import {currentPage, updatePagination} from './pagination.js';

async function fetchImages(page) {
    try {
        const token = localStorage.getItem('token');
        const isAdmin = localStorage.getItem("isAdmin") === "true";
        const url = isAdmin ? '/storage/admin/image?page=' : '/storage/image?page=';
        const response = await fetch(`${url}${page}`, {
            method: "GET",
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        console.log(response);

        if (!response.ok) {
            console.error(`HTTP error! Status: ${response.status}`);
            return;
        }

        const images = await response.json();
        const imagesContainer = document.getElementById('images');

        imagesContainer.innerHTML = images.map(image => `
            <div>
                <p>User with username: ${image.username} uploaded image</p>
                <img src="/storage/image/${image.id}" alt="${image.name}" />
                <p>Uploaded Time: ${image.uploadedTime}</p>
                <p>Image Name: ${image.name}</p>
                <button class="delete-image-button" onclick="deleteImageById('${image.id}')">Delete</button>
            </div>
        `).join('');
        const paginationInfo = {
            totalImages: images.totalCount,
            currentPage: page,
            imagesPerPage: images.data.length
        };
        updatePagination(paginationInfo, fetchImages);
    } catch (error) {
        console.error('fetch error:', error);
    }
}


document.getElementById('uploadForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const fileInput = document.getElementById('fileInput');
    if (!fileInput.files.length) {
        console.error("No file selected!");
        return;
    }
    const formData = new FormData();
    formData.append('file', fileInput.files[0]);

    try {
        const response = await fetch('/storage/upload', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
            },
            body: formData
        });

        const data = await response.json();
        location.reload();
    } catch (error) {
        console.error('Error during upload:', error);
    }
});

window.deleteImageById = async function deleteImage(imageId) {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`/storage/image/${imageId}`, {
            method: "DELETE",
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (response.ok) {
            console.log("Image deleted successfully");
            await fetchImages(currentPage);
        } else {
            console.error(`HTTP error! Status: ${response.status}`);
        }
    } catch (error) {
        console.error('Delete error:', error);
    }
}

fetchImages(currentPage);