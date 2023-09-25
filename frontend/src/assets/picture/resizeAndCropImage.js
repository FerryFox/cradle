export function resizeAndCropImage(file, maxWidth, maxHeight, callback) {
    const reader = new FileReader();

    reader.onload = function (event) {
        const img = new Image();

        img.onload = function () {
            const canvas = document.createElement('canvas');

            let width = img.width;
            let height = img.height;
            let newWidth, newHeight;
            let offsetX = 0, offsetY = 0;

            const targetAspect = maxWidth / maxHeight;
            const imageAspect = width / height;

            if (imageAspect > targetAspect) {
                // Image is wider
                newHeight = maxHeight;
                newWidth = (newHeight * width) / height;
                offsetX = (newWidth - maxWidth) / 2;
            } else {
                // Image is taller or has the same aspect ratio
                newWidth = maxWidth;
                newHeight = (newWidth * height) / width;
                offsetY = (newHeight - maxHeight) / 2;
            }

            canvas.width = maxWidth;
            canvas.height = maxHeight;

            const ctx = canvas.getContext("2d");
            ctx.drawImage(img, -offsetX, -offsetY, newWidth, newHeight);

            callback(canvas.toDataURL("image/jpeg"));
        }

        img.src = event.target.result;
    }

    reader.readAsDataURL(file);
}