import axios from "axios";

export async function createStampCardFromTemplateId(id)
{
    const token = localStorage.getItem('authToken');
    const response = await axios
        .post("/api/stampcard/create", id, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => { console.log(response.data); })
        .catch(error => { console.log(error); });
}