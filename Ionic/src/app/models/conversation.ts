import { User } from './user';

export interface Message {
  id: number;
  content: string;
  senderId: number;
  sentTime: string;
  seenTime: string;
}

export interface Conversation {
  id: number;
  lastUpdated: string;
  doctorDeleted: boolean;
  patientDeleted: boolean;
  messages: Message[];
  patient: User;
  doctor: User;
}
