import api from "./api";

export const uploadBanners = async (jsonPayload, previewImage, cardImages) => {
  // const toastView = createToast(defaultToastMessages.uploadingData);
  const form = new FormData();
  form.append("json", JSON.stringify(jsonPayload));
  form.append("previewImage", previewImage);
  cardImages.map((image) => {
    form.append("cardImages", image);
  });
  try {
    const response = await api.post("/banners/add/", form, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    // updateToast(toastView, response);
    console.log(response);
    return true;
  } catch (error) {
    console.log(error);
    // warningToast(toastView, error);
  }
};
