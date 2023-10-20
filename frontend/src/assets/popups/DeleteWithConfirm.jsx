import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";

function DeleteWithConfirm({ onDelete }) {
    const [open, setOpen] = useState(false);
    const [inputValue, setInputValue] = useState('');

    const handleOpen = () => setOpen(true);
    const handleClose = () => {
        setOpen(false);
        setInputValue(''); // Clear the input when the modal is closed
    };

    const handleDelete = () => {
        if (inputValue.toLowerCase() === 'delete') {
            onDelete();
            handleClose();
        } else {
            alert('You must type "delete" to confirm.');
        }
    };

return (
    <Container>
        <Box>
            <Button variant="contained" color="secondary" onClick={handleOpen}>
                Delete
            </Button>
            <Modal open={open} onClose={handleClose}>
                <Box sx={{
                    p: 2, // Increase padding
                    m: 2, // Add margin
                    background: "blue",
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    display: 'flex', // Added for better layout management
                    flexDirection: 'column', // Added for better layout management
                    gap: 2, // Added for better spacing between elements
                    alignItems: 'center', // Center the items horizontally
                }}>
                    <Typography> {/* Added bottom margin */}
                        Are you sure you want to delete this item? This action cannot be undone!
                    </Typography>
                    <TextField
                        value={inputValue}
                        onChange={(e) => setInputValue(e.target.value)}
                        placeholder="Type 'delete' to confirm"
                    />
                    <Button variant="contained" color="error" onClick={handleDelete}>
                        Confirm Delete
                    </Button>
                </Box>
            </Modal>
        </Box>
    </Container>
    );
}

export default DeleteWithConfirm;
