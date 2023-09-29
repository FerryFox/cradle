import Card from "@mui/material/Card";
import {Box, CardHeader, Divider, Typography} from "@mui/material";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";

function IntroductionCard({ feature, order  })
{
const isImageOnRight = order % 2 === 0;

return(
    <Card sx={{
        display: 'flex',
        flexDirection: isImageOnRight ? 'row-reverse' : 'row',
        mb: 1, width: '100%',
        height : "22vh"}}>

        <CardMedia
            component="img"
            sx={{ width: "40%", objectFit: 'cover' }}
            image={feature.imageUrl}
            alt="Feature"
        />

        <CardContent sx={{ flex: '1 0 auto', width: "50%" }}>
            <Typography  variant="h6">
                {feature.title}
            </Typography>
            <Divider sx={{ mb: 1 , height: '3px' }}/>
            <Typography variant="body2" color="text.secondary">
                {feature.description}
            </Typography>
        </CardContent>
    </Card>
    );
}

export default IntroductionCard;