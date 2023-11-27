import axios from "axios";

export const uploadStories = async (jsonPayload) => {
  // TODO: Информативный вывод и проверка по статусам ответа
  try {
    await axios.post("/stories/add", jsonPayload, {
      headers: { "Content-Type": "application/json" },
    });
    return true;
  } catch (error) {
    return false;
  }
};
