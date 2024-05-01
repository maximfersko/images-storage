let currentPage = 0;

function updatePagination({totalImages, currentPage, imagesPerPage}, fetchImagesCallback) {
    const totalPages = Math.ceil(totalImages / imagesPerPage);
    const pageInfo = document.getElementById('page-info');
    const imageCount = document.getElementById('image-count');
    pageInfo.textContent = `Page ${currentPage} of ${totalPages}`;


    prevButton.disabled = currentPage <= 1;
    prevButton.onclick = () => fetchImagesCallback(currentPage - 1);

    nextButton.disabled = currentPage >= totalPages;
    nextButton.onclick = () => fetchImagesCallback(currentPage + 1);

    imageCount.textContent = `${Math.min(imagesPerPage, totalImages - (currentPage - 1) * imagesPerPage)} images on this page`;
}


export {currentPage, updatePagination};