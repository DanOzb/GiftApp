package com.example.giftapp.fakes

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.every
import io.mockk.mockk

fun FirebaseFirestore.stubDocumentGet(
    collection: String,
    documentId: String,
    data: Map<String, Any?>?,
): DocumentReference {
    val docRef = mockk<DocumentReference>(relaxed = true)
    val snapshot = mockk<DocumentSnapshot>(relaxed = true)

    every { snapshot.exists() } returns (data != null)
    every { snapshot.data } returns data

    every { docRef.get() } returns Tasks.forResult(snapshot)
    // delete() is stubbed to succeed by default; tests can override if needed.
    every { docRef.delete() } returns Tasks.forResult(null)
    // set() also succeeds by default.
    every { docRef.set(any()) } returns Tasks.forResult(null)

    val colRef = mockk<CollectionReference>(relaxed = true)
    every { colRef.document(documentId) } returns docRef
    every { this@stubDocumentGet.collection(collection) } returns colRef

    return docRef
}

fun FirebaseFirestore.stubDocumentSet(
    collection: String,
    documentId: String,
): DocumentReference {
    val docRef = mockk<DocumentReference>(relaxed = true)
    every { docRef.set(any()) } returns Tasks.forResult(null)

    val colRef = mockk<CollectionReference>(relaxed = true)
    every { colRef.document(documentId) } returns docRef
    every { this@stubDocumentSet.collection(collection) } returns colRef

    return docRef
}
