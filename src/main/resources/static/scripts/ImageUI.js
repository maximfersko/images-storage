import {dateFormater} from "./utils/dateFormatter.js";
import {Pagination} from "./Pagination.js";

export class ImageUI {
    constructor(imageService) {
        this.imageService = imageService;
    }

    updatePagination() {
        const pagination = new Pagination(
            this.imageService.currentPage,
            this.imageService.totalPages,
            (newPage) => this.refreshImages(newPage)
        );
        pagination.updatePaginationControls();
    }

    updateImages(images) {
        const imagesContainer = document.getElementById('images');
        imagesContainer.innerHTML = images.map(image => `
    <div>
        <p>User with username: ${image.username} uploaded image</p>
        <img src="/storage/image/${image.id}" alt="${image.name}" />
        <p>Uploaded Time: ${dateFormater(image.uploadedTime)}</p>
        <p>Image Name: ${image.name}</p>
        <button class="delete-image-button" data-image-id="${image.id}">Delete</button>
    </div>
`).join('');
        const imagesCount = document.getElementById('image-count');
        imagesCount.innerHTML = `Showing ${images.length} images on this page`;
        const deleteButtons = imagesContainer.querySelectorAll('.delete-image-button');
        deleteButtons.forEach(button => {
            button.addEventListener('click', (event) => {
                const imageId = event.target.dataset.imageId;
                this.handleDelete(imageId);
            });
        });
        this.updatePagination()
    }

    async handleDelete(imageId) {
        try {
            await this.imageService.deleteImageById(imageId);
            await this.refreshImages();
        } catch (error) {
            console.error("Delete error: ", error);
        }
    }

    async refreshImages() {
        try {
            const images = await this.imageService.fetchImages(this.imageService.currentPage);
            this.updateImages(images);
        } catch (error) {
            console.error("Refresh error: ", error);
        }
    }
}
