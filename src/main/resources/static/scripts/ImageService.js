export class ImageService {
    constructor(apiUrl) {
        this.apiUrl = apiUrl;
        this.token = localStorage.getItem('token');
        this.currentPage = 0;
        this.totalPages = 0;
    }

    async fetchImages(page = 0) {
        const isAdmin = localStorage.getItem("isAdmin") === "true";
        console.log(isAdmin)
        const url = isAdmin ? `${this.apiUrl}/admin/image?page=${page}` : `${this.apiUrl}/image?page=${page}`;
        console.log(url)
        const response = await fetch(url, {
            method: "GET",
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        this.totalPages = data.totalPages;
        this.currentPage = data.currentPage;
        return data.images;
    }

    async getUserInfo() {
        try {
            const response = await fetch(`${this.apiUrl}/user/info`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${this.token}`
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const userInfo = await response.json();
            const isAdmin = userInfo.role === "ADMINISTRATOR";
             localStorage.setItem("isAdmin",isAdmin);
        } catch (error) {
            console.error('Error fetching user info:', error);
        }
    }

    async deleteImageById(imageId) {
        const response = await fetch(`${this.apiUrl}/image/${imageId}`, {
            method: "DELETE",
            headers: {
                'Authorization': `Bearer ${this.token}`
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }

    async uploadFile(fileInput) {
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
    }
}