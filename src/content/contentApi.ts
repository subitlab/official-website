import type {SiteContent} from "@/content/siteContent";

export type CmsRole = "NONE" | "EDITOR" | "ADMIN";

export interface CmsMe {
  userId: number;
  username: string;
  email?: string;
  role: CmsRole;
  csrfToken: string;
}

export interface CmsUser {
  userId: number;
  username?: string;
  email?: string;
  role: CmsRole;
}

export interface ReleaseInfo {
  id: string;
  status: "SCHEDULED" | "PUBLISHED" | "SUPERSEDED" | "CANCELLED";
  scheduledAt?: string;
  publishedAt?: string;
  publishedBy: number;
}

interface DraftResponse { content: SiteContent; updatedAt?: string; updatedBy?: number }
interface PublishResponse { release: ReleaseInfo }
interface UploadResponse { url: string; sha256: string; mediaType: string; bytes: number }

export const CONTENT_API_BASE = import.meta.env.VITE_CONTENT_API_BASE || "/content-api/api";

async function request<T>(path: string, init: RequestInit = {}): Promise<T> {
  const response = await fetch(`${CONTENT_API_BASE}${path}`, {credentials: "include", ...init});
  if (!response.ok) {
    const payload = await response.json().catch(() => null) as {error?: string} | null;
    throw new Error(payload?.error || `内容服务请求失败（${response.status}）`);
  }
  if (response.status === 204) return undefined as T;
  return response.json() as Promise<T>;
}

function jsonRequest(method: string, csrfToken: string, body?: unknown): RequestInit {
  return {
    method,
    headers: {"Content-Type": "application/json", "X-CSRF-Token": csrfToken},
    body: body === undefined ? undefined : JSON.stringify(body),
  };
}

export async function getMe(): Promise<CmsMe | null> {
  const response = await fetch(`${CONTENT_API_BASE}/auth/me`, {credentials: "include"});
  if (response.status === 401) return null;
  if (!response.ok) throw new Error(`内容服务请求失败（${response.status}）`);
  return response.json() as Promise<CmsMe>;
}

export const loginUrl = `${CONTENT_API_BASE}/auth/login`;
export const loadDraft = () => request<DraftResponse>("/content/draft");
export const saveDraft = (content: SiteContent, csrf: string) =>
  request<DraftResponse>("/content/draft", jsonRequest("PUT", csrf, {content}));
export const publishContent = (content: SiteContent, csrf: string, publishAt?: string) =>
  request<PublishResponse>("/content/publish", jsonRequest("POST", csrf, {content, publishAt}));
export const listReleases = () => request<ReleaseInfo[]>("/content/releases");
export const loadReleaseContent = (id: string) => request<SiteContent>(`/content/releases/${encodeURIComponent(id)}`);
export const rollbackRelease = (id: string, csrf: string) =>
  request<PublishResponse>(`/content/releases/${id}/rollback`, jsonRequest("POST", csrf));
export const cancelRelease = (id: string, csrf: string) =>
  request<void>(`/content/releases/${id}`, jsonRequest("DELETE", csrf));
export const listUsers = () => request<CmsUser[]>("/admin/users");
export const updateUserRole = (id: number, role: CmsRole, csrf: string) =>
  request<CmsUser>(`/admin/users/${id}/role`, jsonRequest("PUT", csrf, {role}));
export const logout = (csrf: string) => request<void>("/auth/logout", jsonRequest("POST", csrf));

export async function uploadAsset(file: File, csrf: string): Promise<UploadResponse> {
  const form = new FormData();
  form.append("file", file);
  return request<UploadResponse>("/content/assets", {
    method: "POST",
    headers: {"X-CSRF-Token": csrf},
    body: form,
  });
}
