export class Pagination {
    constructor(currentPage, totalPages, onPageChange) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.onPageChange = onPageChange;

        this.prevButton = document.getElementById('prev');
        this.nextButton = document.getElementById('next');
        this.pageInfo = document.getElementById('page-info');

        this.prevButton.addEventListener('click', () => this.onPrevClick());
        this.nextButton.addEventListener('click', () => this.onNextClick());
    }

    updatePaginationControls() {
        this.pageInfo.textContent = `Page ${this.currentPage + 1} of ${this.totalPages}`;
        this.prevButton.disabled = this.currentPage <= 0;
        this.nextButton.disabled = this.currentPage >= this.totalPages - 1;
    }

    onPrevClick() {
        if (this.currentPage > 0) {
            this.currentPage--;
            this.onPageChange(this.currentPage);
        }
    }

    onNextClick() {
        if (this.currentPage < this.totalPages - 1) {
            this.currentPage++;
            this.onPageChange(this.currentPage);
        }
    }
}