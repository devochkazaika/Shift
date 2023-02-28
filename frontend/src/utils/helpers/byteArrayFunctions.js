export async function convertImageToByteArray(image) {
  const buffer = await image.arrayBuffer();
  let byteArray = new Uint8Array(buffer);
  return Array.from(byteArray);
}

export function convertByteArrayToSrc(byteArray) {
  const content = new Uint8Array(byteArray);
  return URL.createObjectURL(new Blob([content.buffer], { type: 'image/*' }));
}
