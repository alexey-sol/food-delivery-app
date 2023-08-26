import React, { type FC, type PropsWithChildren } from 'react';
import { Box, Container } from '@mui/material';
import { Header } from './header';

export const AppLayout: FC<PropsWithChildren> = ({ children }) => (
  <Container
    maxWidth="lg"
    sx={{
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
    }}
  >
    <Header />

    <Box sx={{ py: 2, flex: 1 }}>
      {children}
    </Box>
  </Container>
);
