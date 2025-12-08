export interface MessageRequest {
  senderId: number;
  receiverId: number;
  consultationId: number;
  content: string;
}

export interface MessageResponse {
  id: number;
  senderId: number;
  receiverId: number;
  consultationId: number;
  content: string;
  sentAt: string; // ISO String
  readFlag: boolean;
}

// Interfaz auxiliar para facilitar el manejo en la vista (Sidebar)
export interface ChatThread {
  consultationId: number;
  partnerId: number;      // El ID del OTRO usuario (con quien hablas)
  lastMessage: string;
  lastDate: Date;
  messages: MessageResponse[]; // Historial completo de este chat
}
