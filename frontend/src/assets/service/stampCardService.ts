import axios from "axios";

export async function createStampCardFromTemplateId(id: number)
{
    const token = localStorage.getItem('authToken');
    await axios
        .post("/api/stampcard/create", id, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });
}