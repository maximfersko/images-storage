import {ImageService} from "./ImageService.js";
import {ImageUI} from "./ImageUI.js";

const imageService = new ImageService("/storage");
const imageUI = new ImageUI(imageService);
imageService.getUserInfo().then(() => {
    imageUI.refreshImages();
});

document.getElementById('uploadForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fileInput = document.getElementById('fileInput');
    await imageService.uploadFile(fileInput);
});

document.getElementById('next').addEventListener('click', () => {
    if (imageService.currentPage < imageService.totalPages - 1) {
        imageUI.refreshImages(++imageService.currentPage);
    }
});

document.getElementById('prev').addEventListener('click', () => {
    if (imageService.currentPage > 0) {
        imageUI.refreshImages(--imageService.currentPage);
    }
});
