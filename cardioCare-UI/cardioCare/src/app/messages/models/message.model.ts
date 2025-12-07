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
  sentAt: string; // ISO Date
  readFlag: boolean;
}
