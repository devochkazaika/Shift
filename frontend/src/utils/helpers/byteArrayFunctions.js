import cloneDeep from 'lodash/cloneDeep';
import { map } from 'modern-async';

export async function convertImageToByteArray(image) {
  const buffer = await image.arrayBuffer();
  let byteArray = new Uint8Array(buffer);
  return Array.from(byteArray);
}

export function convertByteArrayToSrc(byteArray) {
  const content = new Uint8Array(byteArray);
  return URL.createObjectURL(new Blob([content.buffer], { type: 'image/*' }));
}

export const convertToPayload = async (values) => {
  const payload = cloneDeep(values);
  await map(payload.stories, async (story) => {
    story.previewUrl = story.previewUrl && (await convertImageToByteArray(story.previewUrl));
    await map(story.storyFrames, async (frame) => {
      frame.pictureUrl = frame.pictureUrl && (await convertImageToByteArray(frame.pictureUrl));
      return frame;
    });
    return story;
  });

  return payload;
};
