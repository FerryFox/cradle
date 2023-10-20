export function resizeAndCropImage(
    file: File,
    maxWidth: number,
    maxHeight: number,
    callback: (dataUrl: string) => void
): void {
    const reader = new FileReader();

    reader.onload = function(event: ProgressEvent<FileReader>) {
        const img = new Image();

        img.onload = function() {
            const canvas = document.createElement('canvas');

            let width = img.width;
            let height = img.height;
            let newWidth: number, newHeight: number;
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
            if (ctx) {
                ctx.drawImage(img, -offsetX, -offsetY, newWidth, newHeight);
                callback(canvas.toDataURL("image/jpeg"));
            }
        }

        if (typeof event.target?.result === "string") {
            img.src = event.target.result;
        }
    }

    reader.readAsDataURL(file);
}
